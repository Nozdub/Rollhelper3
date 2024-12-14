package com.example.rollhelper3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rollhelper3.ui.main.MainRollPage
import com.example.rollhelper3.ui.navigation.AppNavigation
import com.example.rollhelper3.ui.theme.RollHelper3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RollHelper3Theme {
                AppNavigation() // Connects navigation
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainRollPagePreview() {
    RollHelper3Theme {
    }
}
