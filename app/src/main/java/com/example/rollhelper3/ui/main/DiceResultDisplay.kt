package com.example.rollhelper3.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rollhelper3.ui.utils.getAnimationFramesForDiceResourceIds
import com.example.rollhelper3.ui.utils.getStillImageForResultResourceId
import com.example.rollhelper3.ui.utils.maxDiceValue
import kotlinx.coroutines.delay


@Composable
fun DiceResultDisplay(
    selectedDiceList: List<Pair<String, Int>>, // List of selected dice types
    rollResults: List<Int>,                    // List of roll results
    modifier: Modifier = Modifier
) {
    // Display the dice grid
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp), // Adaptive grid based on screen size
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp) // Constrain the height to avoid infinite height
    ) {
        items(selectedDiceList.size) { index ->
            val (diceType, _) = selectedDiceList[index]
            val currentResult = rollResults.getOrNull(index) ?: maxDiceValue(diceType)
            val animationFrames = getAnimationFramesForDiceResourceIds(diceType)

            // MutableState to track the animation frame or result
            var currentImageResId by remember { mutableStateOf(getStillImageForResultResourceId(diceType, maxDiceValue(diceType))) }

            // Reset animation and update for a new roll
            LaunchedEffect(key1 = rollResults) {
                if (rollResults.isNotEmpty()) {
                    // Roll animation: iterate through frames
                    for (frameResId in animationFrames) {
                        currentImageResId = frameResId
                        delay(100L) // Animation delay
                    }
                    // Set to final rolled result image
                    currentImageResId = getStillImageForResultResourceId(diceType, currentResult)
                } else {
                    // Reset to default side if there's no roll
                    currentImageResId = getStillImageForResultResourceId(diceType, maxDiceValue(diceType))
                }
            }

            // Display the dice image
            Image(
                painter = painterResource(id = currentImageResId),
                contentDescription = "$diceType result $currentResult",
                modifier = Modifier
                    .size(60.dp)
                    .padding(4.dp)
            )
        }
    }
}
