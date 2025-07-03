package com.zinchuk.moviescatalog.ui.components.pager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.zinchuk.moviescatalog.screens.all.AllMoviesTab
import com.zinchuk.moviescatalog.screens.favorites.FavouriteMoviesTab
import kotlinx.coroutines.launch

@Composable
fun MoviesPager() {
    val tabs = Tab.entries
    val pagerState = rememberPagerState(
        pageCount = { tabs.size }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            val scope = rememberCoroutineScope()
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(tab.name.capitalize(Locale.current)) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            when (Tab.entries[page]) {
                Tab.ALL -> AllMoviesTab()
                Tab.FAVOURITE -> FavouriteMoviesTab()
            }
        }
    }
}
