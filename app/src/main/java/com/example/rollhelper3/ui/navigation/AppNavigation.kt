package com.example.rollhelper3.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rollhelper3.ui.components.PageIndicator
import com.example.rollhelper3.ui.history.HistoryPage
import com.example.rollhelper3.ui.main.MainRollPage
import com.example.rollhelper3.ui.profile.ProfilePage
import com.example.rollhelper3.ui.storage.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val pagerState = rememberPagerState(
        initialPage = 1, // Default to MainRollPage
        pageCount = { 3 } // Total number of pages
    )

    // State for roll history
    val rollHistory = remember { mutableStateListOf<Triple<List<Int>, Int, Int>>() }

    // Other states
    val selectedDiceList = remember { mutableStateListOf<Pair<String, Int>>() }
    val rollResults = remember { mutableStateOf<List<Int>>(emptyList()) }
    var hasRolled by remember { mutableStateOf(false) }
    var shouldAnimate by remember { mutableStateOf(false) }

    // DataStore
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val coroutineScope = rememberCoroutineScope()

    // Load roll history once when the app starts
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            dataStoreManager.getRollHistory().collect { savedHistory ->
                rollHistory.clear()
                rollHistory.addAll(savedHistory)
            }
        }
    }

    // Save roll history whenever it changes
    LaunchedEffect(rollHistory.toList()) {
        coroutineScope.launch {
            if (rollHistory.isNotEmpty()) {
                dataStoreManager.saveRollHistory(rollHistory)
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    onHasRolledUpdated = { newHasRolled ->
                        hasRolled = newHasRolled
                        if (newHasRolled) shouldAnimate = true
                    },
                    isFocused = pagerState.currentPage == 1,
                    onRollResultsUpdated = { newRollResults ->
                        rollResults.value = newRollResults
                    },
                    shouldAnimate = shouldAnimate,
                    dataStoreManager = dataStoreManager
                )

                2 -> ProfilePage(dataStoreManager = dataStoreManager)
            }
        }
        PageIndicator(
            currentPage = pagerState.currentPage,
            pageCount = 3,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        )


        // Reset animation flag when leaving the MainRollPage
        LaunchedEffect(pagerState.currentPage) {
            if (pagerState.currentPage != 1) {
                shouldAnimate = false
            }
        }
    }
}
