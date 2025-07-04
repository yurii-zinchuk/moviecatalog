package com.zinchuk.moviescatalog.test_utils

import androidx.recyclerview.widget.DiffUtil
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieListItem

internal class MovieListItemDiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
    override fun areItemsTheSame(
        oldItem: MovieListItem,
        newItem: MovieListItem,
    ): Boolean {
        return when {
            oldItem is MovieListItem.Movie && newItem is MovieListItem.Movie ->
                oldItem.movie.id == newItem.movie.id
            oldItem is MovieListItem.Header && newItem is MovieListItem.Header ->
                oldItem.label == newItem.label
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: MovieListItem,
        newItem: MovieListItem,
    ): Boolean {
        return oldItem == newItem
    }
}
