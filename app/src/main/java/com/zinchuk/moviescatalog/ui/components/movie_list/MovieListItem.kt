package com.zinchuk.moviescatalog.ui.components.movie_list

import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData

internal sealed interface MovieListItem {
    data class Header(val label: String) : MovieListItem
    data class Movie(val movie: MovieCardData) : MovieListItem
}
