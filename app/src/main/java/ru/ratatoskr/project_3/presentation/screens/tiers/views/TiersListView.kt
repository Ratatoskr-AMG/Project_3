package ru.ratatoskr.project_3.presentation.screens.tiers.views

import android.content.SharedPreferences
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import ru.ratatoskr.project_3.domain.utils.rememberForeverLazyListState
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersState

@ExperimentalFoundationApi
@Composable
fun TiersListView(
    navController: NavController,
    viewState: TiersState,
    appSharedPreferences: SharedPreferences,
    onTierChange: (String) -> Unit,
) {
    var scrollState = rememberForeverLazyListState(key = "Tiers")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x55202020))
        ) {

            stickyHeader {
                TiersHeaderView(navController, viewState, appSharedPreferences)
            }
            for (i in 0..8) {
                item {
                    TierRowView(
                        i,
                        viewState,
                        onTierChange
                    )
                }
            }
        }
    }
}