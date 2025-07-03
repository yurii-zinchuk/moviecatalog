package com.zinchuk.moviescatalog.ui.components.movie_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCard
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData

@Composable
internal fun MovieList(
    items: LazyPagingItems<MovieListItem>,
    onLikeToggle: (MovieCardData) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = items.itemCount,
            key = { idx -> items[idx]?.hashCode() ?: 0 },
        ) { idx ->
            when (val item = items[idx]) {
                is MovieListItem.Header -> {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp),
                    )
                }

                is MovieListItem.Movie -> {
                    MovieCard(
                        movie = item.movie,
                        onLikeToggle = { onLikeToggle(item.movie) },
                        onShareClick = { /* TODO: Implement share */ },
                    )
                }

                null -> {
                    // no-op
                }
            }
        }

        items.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillParentMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
