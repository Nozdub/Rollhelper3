package com.example.rollhelper3.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.rollhelper3.R
import com.example.rollhelper3.ui.components.AppTopBar
import com.example.rollhelper3.ui.components.DragonAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRollPage(
    modifier: Modifier = Modifier,
    rollHistory: MutableList<List<Int>>,
    selectedDiceList: MutableList<Pair<String, Int>>,
    rollResults: List<Int>,
    hasRolled: Boolean,
    onHasRolledUpdated: (Boolean) -> Unit,
    isFocused: Boolean,
    onRollResultsUpdated: (List<Int>) -> Unit,
    shouldAnimate: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDragonAnimation by remember { mutableStateOf(false) }
    val rollTotal = rollResults.sum()

    Scaffold(
        topBar = { AppTopBar() },
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Dice Chips Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DiceTypeChips(
                                onDiceSelected = { diceType ->
                                    if (selectedDiceList.size < 25) { // Limit dice to 25
                                        if (hasRolled) {
                                            // Clear everything if new dice are selected after rolling
                                            selectedDiceList.clear()
                                            onRollResultsUpdated(emptyList())
                                            onHasRolledUpdated(false)
                                        }
                                        selectedDiceList.add(diceType to 1)

                                        // Show warning snackbar at 20 dice
                                        if (selectedDiceList.size == 20) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Careful, you're poking the dragon",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    } else if (selectedDiceList.size == 25) {
                                        showDragonAnimation = true // Trigger the dragon animation
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Maximum of 25 dice allowed.",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                }
                            )

                            // Undo and Clear Buttons
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    if (selectedDiceList.isNotEmpty()) {
                                        selectedDiceList.removeLast()
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.undo_arrow_icon),
                                        contentDescription = "Undo",
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                }

                                Text(
                                    text = "Pick your dice!",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(2f, 2f),
                                            blurRadius = 4f
                                        )
                                    ),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                                IconButton(onClick = {
                                    selectedDiceList.clear()
                                    onRollResultsUpdated(emptyList())
                                    onHasRolledUpdated(false)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.brush_icon),
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier
                                            .size(24.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Dice Results with Spacer
                    Box(
                        modifier = Modifier
                            .weight(1f) // Use weight to let DiceResultDisplay grow
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        DiceResultDisplay(
                            selectedDiceList = selectedDiceList,
                            rollResults = rollResults,
                            shouldAnimate = hasRolled && isFocused && shouldAnimate, // Only animate on new rolls
                            hasRolled = hasRolled,
                            rollTotal = rollTotal,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Add Spacer and RollButton at the bottom
                    Spacer(modifier = Modifier)
                    RollButton(
                        selectedDiceList = selectedDiceList,
                        onRoll = { rolledResults ->
                            coroutineScope.launch {
                                if (selectedDiceList.isEmpty()) {
                                    snackbarHostState.showSnackbar(
                                        message = "Select some dice first!",
                                        duration = SnackbarDuration.Short
                                    )
                                } else {
                                    onRollResultsUpdated(rolledResults)
                                    onHasRolledUpdated(true)

                                    // Update the roll history
                                    rollHistory.add(rolledResults)
                                    if (rollHistory.size > 10) {
                                        rollHistory.removeFirst()
                                    }
                                }
                            }
                        },
                        snackbarHostState = snackbarHostState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                // Show Dragon Animation when triggered
                if (showDragonAnimation) {
                    DragonAnimation(
                        context = LocalContext.current,
                        isTriggered = showDragonAnimation,
                        onFrameUpdate = { frameIndex ->
                            coroutineScope.launch {
                                // Clear dice row by row based on the frame index
                                if (frameIndex % 2 == 0 && selectedDiceList.isNotEmpty()) {
                                    delay(1100L)
                                    val numDicePerRow = 5 // Dices per row
                                    val currentSize = selectedDiceList.size // Adjust based on your dice layout
                                    val clearCount = minOf(numDicePerRow, currentSize)
                                    delay(270L)

                                    // Remove the last 'clearCount' dice
                                    repeat(clearCount) {
                                        if (selectedDiceList.isNotEmpty()) {
                                            selectedDiceList.removeLast()

                                        }
                                    }
                                }
                            }
                        },
                        onAnimationEnd = {
                            showDragonAnimation = false
                            onRollResultsUpdated(emptyList())
                        }
                    )
                }


            }
        }
    )
}
