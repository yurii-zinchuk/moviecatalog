package com.zinchuk.moviescatalog.screens.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zinchuk.moviescatalog.R
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieList

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun AllMoviesTab(vm: AllMoviesViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()

    when (val uiState = state) {
        is AllMoviesUiState.Loading -> {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        is AllMoviesUiState.Success -> {
            val items = uiState.movies.collectAsLazyPagingItems()

            items.run {
                when {
                    loadState.refresh is LoadState.Error -> {
                        vm.onLoadError()
                    }

                    loadState.refresh is LoadState.Loading && itemCount == 0 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {}
                }
            }

            PullToRefreshBox(
                isRefreshing = items.loadState.refresh is LoadState.Loading && items.itemCount > 0,
                onRefresh = items::refresh,
            ) {
                if (items.itemCount > 0) {
                    MovieList(
                        items = items,
                        onLikeToggle = vm::onToggleFavorite,
                    )
                }
            }
        }

        is AllMoviesUiState.Error -> {
            val cachedItems = uiState.cached.collectAsLazyPagingItems()

            PullToRefreshBox(
                isRefreshing = cachedItems.loadState.refresh is LoadState.Loading && cachedItems.itemCount > 0,
                onRefresh = vm::loadMovies,
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.error_load_failed),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    if (cachedItems.itemCount > 0) {
                        Text(
                            text = stringResource(R.string.message_showing_cached),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MovieList(
                            items = cachedItems,
                            onLikeToggle = vm::onToggleFavorite,
                        )
                    } else if (cachedItems.loadState.refresh !is LoadState.Loading) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(stringResource(R.string.error_no_cached))
                        }
                    }
                }
            }
        }
    }
}
