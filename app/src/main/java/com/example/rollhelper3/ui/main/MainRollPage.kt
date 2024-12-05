package com.example.rollhelper3.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rollhelper3.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRollPage(modifier: Modifier = Modifier) {
    // State management for user selections, roll results, and history
    val selectedDiceList = remember { mutableStateListOf<Pair<String, Int>>() }
    var rollResults by remember { mutableStateOf<List<Int>>(emptyList()) }
    val rollHistory = remember { mutableStateListOf<List<Int>>() } // Track the last 10 rolls
    var hasRolled by remember { mutableStateOf(false) } // Track if a roll has been made

    // Snackbar state and coroutine scope for showing messages
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Defining dragon frames
    val dragonFrames = listOf(
        R.drawable.tile000,
        R.drawable.tile001,
        R.drawable.tile002,
        R.drawable.tile003,
        R.drawable.tile004,
        R.drawable.tile005,
        R.drawable.tile006,
        R.drawable.tile007,
        R.drawable.tile009,
        R.drawable.tile010,
        R.drawable.tile011,
        R.drawable.tile012,
        R.drawable.tile013,
        R.drawable.tile014,
        R.drawable.tile015
    )

    // State of dragon animation
    var showDragonAnimation by remember { mutableStateOf(false) }
    var currentDragonFrame by remember { mutableStateOf(dragonFrames[0]) }

    // Animation state for the dragon's vertical position (Y)
    val dragonVerticalOffset by animateFloatAsState(
        targetValue = if (showDragonAnimation) -300f else 1000f,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing),
        finishedListener = {
            // Once the dragon has finished flying, stop the animation
            showDragonAnimation = false
        }
    )

    Scaffold(
        topBar = {
            // TopAppBar with rounded corners for enhanced visual appeal
            Surface(
                shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 4.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "RollHelper",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()) // Make the whole page scrollable
                    .padding(16.dp)
            ) {
                Column {
                    // Floating Card for Dice Chips
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Padding around the card
                        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
                        elevation = CardDefaults.cardElevation(8.dp) // Card elevation for a floating effect
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Allow the user to select the dice type using Chips
                            DiceTypeChips(
                                onDiceSelected = { diceType ->
                                    if (hasRolled) {
                                        // If a roll has been made, clear everything and start fresh
                                        selectedDiceList.clear()
                                        rollResults = emptyList()
                                        hasRolled = false // Reset the hasRolled flag
                                    }
                                    // Add the selected dice to the list
                                    selectedDiceList.add(diceType to 1)

                                    // Show snackbar if dice count reaches 20
                                    if (selectedDiceList.size == 20) {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Careful, you're poking the dragon!",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                    // Trigger Dragon animation when dice reaches 25
                                    if (selectedDiceList.size >= 25) {
                                        showDragonAnimation = true
                                        coroutineScope.launch {
                                            delay(900L) // Delay to allow the dragon to start flying before clearing

                                            // Gradually clear the dice from bottom to top to mimic the dragon flying over them
                                            val numDicePerRow = 5 // Assuming you want to clear 5 dice at a time

                                            // Removing dice progressively from the bottom
                                            while (selectedDiceList.isNotEmpty()) {
                                                delay(200L) // Delay between each group being cleared
                                                val currentSize = selectedDiceList.size
                                                val clearCount = minOf(numDicePerRow, currentSize)

                                                // Remove the last 'clearCount' elements from the list
                                                repeat(clearCount) {
                                                    selectedDiceList.removeLast()
                                                }
                                            }

                                            // Finally clear roll results after all dice are cleared
                                            rollResults = emptyList()
                                        }
                                    }


                                }
                            )

                            // Undo and Clear Actions
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
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
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Text(
                                    text = "Pick your dice!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                                IconButton(onClick = {
                                    selectedDiceList.clear()
                                    rollResults = emptyList() // Clear current roll results as well
                                    hasRolled = false // Reset the hasRolled flag
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.brush_icon),
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Roll button that starts the dice roll
                    RollButton(
                        selectedDiceList = selectedDiceList,
                        onRoll = {
                            rollResults = it // Update the roll results when the button is pressed
                            hasRolled = true // Set the flag to indicate that a roll has been made

                            // Add a delay to update the history, to ensure animations complete first
                            coroutineScope.launch {
                                delay(1000L) // Delay for animation completion (adjust as needed)
                                // Add the results to the roll history
                                rollHistory.add(it)
                                // Keep only the last 10 entries in the history
                                if (rollHistory.size > 10) {
                                    rollHistory.removeFirst()
                                }
                            }
                        }
                    )

                    // Display the results of the dice roll
                    DiceResultDisplay(
                        selectedDiceList = selectedDiceList,
                        rollResults = rollResults
                    )

                    // Floating Card for Roll History Section
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Padding around the card
                        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
                        elevation = CardDefaults.cardElevation(8.dp) // Card elevation for a floating effect
                    ) {
                        // Display the roll history section
                        RollHistorySection(
                            rollHistory = rollHistory,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                // Show dragon animation overlay, only when active (placed at the end of the Box)
                if (showDragonAnimation) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .offset(y = dragonVerticalOffset.dp), // Animate vertical offset
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = currentDragonFrame),
                            contentDescription = "Flying dragon animation",
                            modifier = Modifier
                                .size(350.dp) // Make the dragon larger
                        )
                    }
                }
            }
        }
    )
}
