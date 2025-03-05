package com.example.rollhelper3.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rollhelper3.ui.utils.getAnimationFramesForDiceResourceIds
import com.example.rollhelper3.ui.utils.getStillImageForResultResourceId
import com.example.rollhelper3.ui.utils.maxDiceValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DiceResultDisplay(
    selectedDiceList: List<Pair<String, Int>>,
    rollResults: List<Int>,
    shouldAnimate: Boolean,
    hasRolled: Boolean,
    rollTotal: Int,
    abilityModifiers: Map<String, Int>,
    isProficiencyEnabled: Boolean,
    proficiencyValue: Int,
    isExpertiseEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    // State for crit mode toggle
    var isCritMode by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Calculate modifiers and totals
    val abilityModifierTotal = abilityModifiers.values.sum()
    val proficiencyBonus = if (isProficiencyEnabled) {
        if (isExpertiseEnabled) proficiencyValue * 2 else proficiencyValue
    } else 0
    val modifierTotal = abilityModifierTotal + proficiencyBonus
    val finalTotal = rollTotal + modifierTotal
    val critTotal = (rollTotal * 2) + modifierTotal

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Dice grid display
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 70.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedDiceList.size) { index ->
                    val (diceType, _) = selectedDiceList[index]
                    val currentResult = rollResults.getOrNull(index) ?: maxDiceValue(diceType)
                    val animationFrames = getAnimationFramesForDiceResourceIds(diceType)
                    var currentImageResId by remember { mutableIntStateOf(getStillImageForResultResourceId(diceType, currentResult)) }

                    // Trigger animation
                    LaunchedEffect(shouldAnimate, rollResults) {
                        if (shouldAnimate) {
                            for (frame in animationFrames) {
                                currentImageResId = frame
                                delay(50L)
                            }
                            currentImageResId = getStillImageForResultResourceId(diceType, currentResult)
                        } else if (hasRolled) {
                            currentImageResId = getStillImageForResultResourceId(diceType, currentResult)
                        } else {
                            currentImageResId = getStillImageForResultResourceId(diceType, maxDiceValue(diceType))
                        }
                    }

                    Image(
                        painter = painterResource(id = currentImageResId),
                        contentDescription = "Dice $diceType showing result $currentResult",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
            // Adding test comment
            // Display the detailed breakdown of totals at the bottom
            if (hasRolled) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp)
                ) {
                    // Crit toggle radio button
                    RadioButton(
                        selected = isCritMode,
                        onClick = {
                            isCritMode = !isCritMode
                            if (isCritMode) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Crit mode enabled! Dice results now doubled.",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add spacing between radio button and text

                    // Totals text
                    Text(
                        text = if (isCritMode) {
                            "Dice: ${rollTotal * 2} | Modifiers: $modifierTotal | Total: $critTotal"
                        } else {
                            "Dice: $rollTotal | Modifiers: $modifierTotal | Total: $finalTotal"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim,
                        letterSpacing = 1.5.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Snackbar Host - Overlay without affecting layout
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
        snackbarHostState



    }
}
