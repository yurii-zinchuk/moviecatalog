package com.zinchuk.moviescatalog.utils

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.zinchuk.moviescatalog.ui.components.movie_card.MovieCardData
import com.zinchuk.moviescatalog.ui.components.movie_list.MovieListItem
import java.text.SimpleDateFormat
import java.util.Locale

internal fun PagingData<MovieCardData>.withDateHeaders(): PagingData<MovieListItem> {
    return this
        .map { MovieListItem.Movie(it) }
        .insertSeparators { before, after ->
            if (after == null) return@insertSeparators null

            val beforeDate = before?.movie?.releaseDate?.takeIf { it.isNotBlank() }
            val afterDate = after.movie.releaseDate?.takeIf { it.isNotBlank() }

            val beforeLabel = beforeDate?.let { formatMonthYear(it) }
            val afterLabel = afterDate?.let { formatMonthYear(it) }

            if (afterLabel != null && beforeLabel != afterLabel) {
                MovieListItem.Header(afterLabel)
            } else {
                null
            }
        }
}

private fun formatMonthYear(dateStr: String): String? {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("MMM yyyy", Locale.US)
        val date = inputFormat.parse(dateStr)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        null
    }
}
