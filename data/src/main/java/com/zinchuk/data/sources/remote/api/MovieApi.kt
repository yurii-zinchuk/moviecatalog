package com.zinchuk.data.sources.remote.api

import com.zinchuk.data.sources.remote.api.dto.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

internal interface MovieApi {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @QueryMap options: Map<String, String> = MovieQueryParams.toQueryMap()
    ): MovieResponseDTO
}

private data object MovieQueryParams {
    private const val VOTE_AVG_THRESHOLD: Double = 7.0
    private const val VOTE_COUNT_THRESHOLD: Int = 100
    private const val SORT_BY: String = "primary_release_date.desc"

    fun toQueryMap(): Map<String, String> = mapOf(
        "vote_average.gte" to VOTE_AVG_THRESHOLD.toString(),
        "vote_count.gte" to VOTE_COUNT_THRESHOLD.toString(),
        "sort_by" to SORT_BY,
    )
}
