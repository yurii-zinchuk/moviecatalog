package com.zinchuk.moviescatalog.screens.all

import androidx.paging.PagingData
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieListItem
import kotlinx.coroutines.flow.Flow

internal sealed interface AllMoviesUiState {
    data object Loading : AllMoviesUiState
    data class Success(
        val movies: Flow<PagingData<MovieListItem>>
    ) : AllMoviesUiState

    data class Error(
        val isRefreshing: Boolean,
        val cached: Flow<PagingData<MovieListItem>>
    ) : AllMoviesUiState
}
