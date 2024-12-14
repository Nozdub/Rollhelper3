package com.example.rollhelper3.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.rollhelper3.ui.components.AppTopBar

@Composable
fun ProfilePage() {
    Scaffold(
        topBar = { AppTopBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Profile Page",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}
