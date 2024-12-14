package com.example.rollhelper3.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.rollhelper3.ui.history.HistoryPage
import com.example.rollhelper3.ui.main.MainRollPage
import com.example.rollhelper3.ui.profile.ProfilePage

@Composable
fun AppNavigation() {
    val pagerState = rememberPagerState { 3 }
    val rollHistory = remember { mutableStateListOf<List<Int>>() }
    val selectedDiceList = remember { mutableStateListOf<Pair<String, Int>>() }
    val rollResults = remember { mutableStateOf<List<Int>>(emptyList()) }
    var hasRolled by remember { mutableStateOf(false) } // Shared hasRolled state
    var shouldAnimate by remember { mutableStateOf(false) } // Control animation separately

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> HistoryPage(rollHistory = rollHistory)
            1 -> MainRollPage(
                rollHistory = rollHistory,
                selectedDiceList = selectedDiceList,
                rollResults = rollResults.value,
                hasRolled = hasRolled,
                onHasRolledUpdated = {
                    hasRolled = it
                    if (it) shouldAnimate = true // Trigger animation only on new roll
                },
                isFocused = pagerState.currentPage == 1,
                onRollResultsUpdated = { rollResults.value = it },
                shouldAnimate = shouldAnimate // Pass shouldAnimate to MainRollPage
            )
            2 -> ProfilePage()
        }
    }

    // Reset animation flag when leaving the MainRollPage
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != 1) {
            shouldAnimate = false // Stop animation when leaving MainRollPage
        }
    }

}
