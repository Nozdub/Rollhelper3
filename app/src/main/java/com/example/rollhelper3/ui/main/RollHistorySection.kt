package com.example.rollhelper3.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RollHistorySection(
    rollHistory: List<List<Int>>, // History of the last 10 rolls
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 300.dp), // Constrain the height of the entire column to avoid height ambiguity
        horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally in the entire column
    ) {
        // History heading that is always visible and centered
        Text(
            text = "History",
            style = MaterialTheme.typography.headlineSmall.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                    blurRadius = 4f
                )
            ),
            color = MaterialTheme.colorScheme.tertiary,
            letterSpacing = 1.5.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )


            // Display the history in a scrollable list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp) // Limit the height for the LazyColumn, allowing it to scroll
            ) {
                items(rollHistory.reversed()) { rolls ->
                    val total = rolls.sum()
                    Text(
                        text = "Rolls: ${rolls.joinToString(separator = " + ")} = Total: $total",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
    }
}

