package com.zinchuk.moviescatalog.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zinchuk.moviescatalog.R
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieList

@Composable
internal fun FavouriteMoviesTab(
    vm: FavouriteMoviesViewModel = viewModel()
) {
    val movies = vm.favoriteMovies.collectAsLazyPagingItems()

    if (movies.itemCount == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.error_no_favorite), style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        MovieList(
            items = movies,
            onLikeToggle = vm::onToggleFavorite,
        )
    }
}
