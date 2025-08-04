package com.chriaasen.rollhelper.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiceTypeChips(
    onDiceSelected: (String) -> Unit,
    diceRows: List<List<String>> = listOf(
        listOf("d4", "d6", "d8"),
        listOf("d10", "d12", "d20")
    ),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(4.dp)) {
        diceRows.forEach { diceTypes ->
            DiceRow(diceTypes, onDiceSelected)
        }
    }
}

@Composable
private fun DiceRow(diceTypes: List<String>, onDiceSelected: (String) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        diceTypes.forEach { diceType ->
            FilterChip(
                selected = false,
                onClick = { onDiceSelected(diceType) },
                label = {
                    Text(
                        text = diceType,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )
        }
    }
}
