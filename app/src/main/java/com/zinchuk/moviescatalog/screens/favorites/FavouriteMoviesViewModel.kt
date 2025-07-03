package com.zinchuk.moviescatalog.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zinchuk.domain.usecases.GetFavoriteMoviesPagedUseCase
import com.zinchuk.domain.usecases.RemoveFavoriteUseCase
import com.zinchuk.moviescatalog.mappers.MovieCardDataTODomainMapper
import com.zinchuk.moviescatalog.mappers.MovieDomainTOCardDataMapper
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieListItem
import com.zinchuk.moviescatalog.utils.withDateHeaders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouriteMoviesViewModel @Inject constructor(
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val movieDomainToCardDataMapper: MovieDomainTOCardDataMapper,
    private val movieCardDataTODomainMapper: MovieCardDataTODomainMapper,
    getFavoriteMoviesPagedUseCase: GetFavoriteMoviesPagedUseCase,
) : ViewModel() {

    val favoriteMovies: Flow<PagingData<MovieListItem>> = getFavoriteMoviesPagedUseCase()
        .map { movieList ->
            movieList.map { movie ->
                movieDomainToCardDataMapper.map(movie, isFavourite = true)
            }
        }
        .map { it.withDateHeaders() }
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    fun onToggleFavorite(movieData: MovieCardData) {
        viewModelScope.launch {
            val movie = movieCardDataTODomainMapper.map(movieData)
            removeFavoriteUseCase(movie)
        }
    }
}
