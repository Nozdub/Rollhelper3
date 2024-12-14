package com.example.rollhelper3.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rollhelper3.ui.components.AppTopBar

@Composable
fun HistoryPage(
    rollHistory: List<List<Int>>
) {
    Scaffold(
        topBar = { AppTopBar() },
        content = { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.background, // Set background color
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Center the Roll History text
                            Text(
                                text = "Roll History",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.tertiary
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(bottom = 8.dp)
                            )

                            // Display newest rolls at the top
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(rollHistory.reversed()) { rolls -> // Reverse the list
                                    Text(
                                        text = "Rolls: ${rolls.joinToString(separator = " + ")} = Total: ${rolls.sum()}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.tertiary
                                        ),
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
