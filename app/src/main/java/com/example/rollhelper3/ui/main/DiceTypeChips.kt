package com.example.rollhelper3.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiceTypeChips(
    onDiceSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val diceTypesRow1 = listOf("d4", "d6", "d8")
    val diceTypesRow2 = listOf("d10", "d12", "d20")

    Column(modifier = modifier.padding(8.dp)) {
        // First row of chips
        Row(modifier = Modifier.padding(8.dp)) {
            diceTypesRow1.forEach { diceType ->
                FilterChip(
                    selected = false,
                    onClick = {
                        onDiceSelected(diceType)
                    },
                    label = {
                        Text(
                            text = diceType,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center, // Make sure text is centered
                            letterSpacing = 2.sp,
                            modifier = Modifier.fillMaxWidth() // Ensure text takes full space to be centered
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f), // Each chip takes up equal space
                )
            }
        }

        // Second row of chips
        Row(modifier = Modifier.padding(8.dp)) {
            diceTypesRow2.forEach { diceType ->
                FilterChip(
                    selected = false,
                    onClick = {
                        onDiceSelected(diceType)
                    },
                    label = {
                        Text(
                            text = diceType,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center, // Make sure text is centered
                            letterSpacing = 2.sp,
                            modifier = Modifier.fillMaxWidth() // Ensure text takes full space to be centered
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f), // Each chip takes up equal space
                )
            }
        }
    }
}
