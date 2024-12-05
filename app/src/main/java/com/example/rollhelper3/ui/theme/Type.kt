package com.example.rollhelper3.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rollhelper3.R

// Create a FontFamily with the Bebas Neue font
val BebasNeueFontFamily = FontFamily(
    Font(R.font.bebasneue_regular, FontWeight.Normal)  // Note: Use the exact name of your TTF resource file without the extension
)

// Define custom typography styles using Bebas Neue
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = BebasNeueFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp // Customize as needed
    ),
    headlineMedium = TextStyle(
        fontFamily = BebasNeueFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp // Customize as needed
    ),
    headlineSmall = TextStyle(
        fontFamily = BebasNeueFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp // Customize as needed
    ),
    bodyLarge = TextStyle(
        fontFamily = BebasNeueFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp // Customize as needed
    ),
    bodyMedium = TextStyle(
        fontFamily = BebasNeueFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp // Customize as needed
    ),
    bodySmall = TextStyle(
        fontFamily = BebasNeueFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp // Customize as needed
    ),

)
