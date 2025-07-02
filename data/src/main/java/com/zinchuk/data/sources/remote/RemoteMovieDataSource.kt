package com.zinchuk.data.sources.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zinchuk.data.BuildConfig
import com.zinchuk.data.sources.remote.api.MovieApi
import com.zinchuk.data.sources.remote.api.dto.MovieDTO
import com.zinchuk.data.sources.remote.api.dto.MovieResponseDTO

internal class RemoteMovieDataSource(
    private val api: MovieApi,
) : PagingSource<Int, MovieDTO>() {

    lateinit var onFirstPageLoaded: suspend (List<MovieDTO>) -> Unit

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDTO> {
        return try {
            val page = params.key ?: PAGE_FIRST
            val response: MovieResponseDTO = api.discoverMovies(
                token = BuildConfig.TMDB_ACCESS_TOKEN,
                page = page,
            )
            val movies = response.results

            if (page == PAGE_FIRST) {
                onFirstPageLoaded.invoke(movies)
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == PAGE_FIRST) null else page.dec(),
                nextKey = if (movies.isEmpty()) null else page.inc()
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.inc()
                ?: state.closestPageToPosition(it)?.nextKey?.dec()
        }
    }

    private companion object {
        private const val PAGE_FIRST = 1
    }
}
