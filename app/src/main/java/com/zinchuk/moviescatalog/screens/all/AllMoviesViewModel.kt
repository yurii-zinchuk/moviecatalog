package com.zinchuk.moviescatalog.screens.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.zinchuk.domain.usecases.AddFavoriteUseCase
import com.zinchuk.domain.usecases.GetCachedMoviesUseCase
import com.zinchuk.domain.usecases.GetPagedMoviesUseCase
import com.zinchuk.domain.usecases.GetFavoriteMoviesUseCase
import com.zinchuk.domain.usecases.RemoveFavoriteUseCase
import com.zinchuk.moviescatalog.mappers.MovieCardDataTODomainMapper
import com.zinchuk.moviescatalog.mappers.MovieDomainTOCardDataMapper
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import com.zinchuk.moviescatalog.utils.withDateHeaders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AllMoviesViewModel @Inject constructor(
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val movieDomainToCardDataMapper: MovieDomainTOCardDataMapper,
    private val movieCardDataTODomainMapper: MovieCardDataTODomainMapper,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getPagedMoviesUseCase: GetPagedMoviesUseCase,
    private val getCachedMoviesUseCase: GetCachedMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AllMoviesUiState>(AllMoviesUiState.Loading)
    val uiState: StateFlow<AllMoviesUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() = viewModelScope.launch {
        val pagingFlow = combine(
            getPagedMoviesUseCase().cachedIn(viewModelScope),
            getFavoriteMoviesUseCase()
        ) { pagingData, favorites ->
            pagingData.map { movie ->
                movieDomainToCardDataMapper.map(
                    data = movie,
                    isFavourite = favorites.any { it.id == movie.id }
                )
            }.withDateHeaders()
        }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

        _uiState.value = AllMoviesUiState.Success(pagingFlow)
    }

    // Get cached movies
    fun onLoadError() = viewModelScope.launch {
        _uiState.value = AllMoviesUiState.Loading

        val cachedFlow = combine(
            getCachedMoviesUseCase().cachedIn(viewModelScope),
            getFavoriteMoviesUseCase()
        ) { pagingData, favorite ->
            pagingData.map { movie ->
                movieDomainToCardDataMapper.map(
                    data = movie,
                    isFavourite = favorite.any { it.id == movie.id }
                )
            }.withDateHeaders()
        }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

        _uiState.value = AllMoviesUiState.Error(
            isRefreshing = false,
            cached = cachedFlow
        )
    }

    fun onToggleFavorite(movieData: MovieCardData) {
        viewModelScope.launch {
            val movie = movieCardDataTODomainMapper.map(movieData)
            if (movieData.isFavorite) {
                removeFavoriteUseCase(movie)
            } else {
                addFavoriteUseCase(movie)
            }
        }
    }
}
