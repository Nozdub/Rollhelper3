package com.example.rollhelper3.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
fun DiceResultDisplay(
    selectedDiceList: List<Pair<String, Int>>,
    rollResults: List<Int>,
    shouldAnimate: Boolean,
    hasRolled: Boolean,
    rollTotal: Int,
    abilityModifiers: Map<String, Int>, // Add ability modifiers
    isProficiencyEnabled: Boolean, // Whether proficiency is enabled
    proficiencyValue: Int, // Proficiency value
    isExpertiseEnabled: Boolean, // Whether expertise is enabled
    modifier: Modifier = Modifier
) {
    // Calculate the total modifiers
    val abilityModifierTotal = abilityModifiers.values.sum()
    val proficiencyBonus = if (isProficiencyEnabled) {
        if (isExpertiseEnabled) proficiencyValue * 2 else proficiencyValue
    } else 0
    val modifierTotal = abilityModifierTotal + proficiencyBonus

    // Calculate the final total
    val finalTotal = rollTotal + modifierTotal

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dice grid display
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

        // Display the detailed breakdown of totals at the bottom
        if (hasRolled) {
            Text(
                text = "Dice: $rollTotal | Modifiers: $modifierTotal | Total: $finalTotal",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim,
                letterSpacing = 1.5.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

