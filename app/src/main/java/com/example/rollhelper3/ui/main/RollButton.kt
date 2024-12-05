package com.example.rollhelper3.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rollhelper3.ui.utils.maxDiceValue

@Composable
fun RollButton(
    selectedDiceList: List<Pair<String, Int>>, // The selected dice in the order they were picked
    onRoll: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            // Generate random results for each selected dice
            val results = selectedDiceList.map { (diceType, _) ->
                val sides = maxDiceValue(diceType)
                (1..sides).random()
            }
            onRoll(results) // Pass the generated results
        },
        modifier = modifier
            .fillMaxWidth() // Make the button fill the available width
            .height(72.dp)
            .padding(8.dp) // Add some padding on the sides for visual appeal
    ) {
        Text(
            text = "Roll Dice",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.tertiary,
            letterSpacing = 2.sp

        )
    }
}
