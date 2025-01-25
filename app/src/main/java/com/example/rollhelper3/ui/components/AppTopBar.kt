package com.example.rollhelper3.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rollhelper3.ui.theme.*

@Composable
fun AppTopBar() {
    Surface(
        shape = RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Display app's title, centered in the top bar for better visibility

            Text(
                text = "RollHelper",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.tertiary,
                letterSpacing = 1.5.sp,
                modifier = Modifier
                    .align(Alignment.Center) // Center the text in the Box

            )
        }
    }
}
