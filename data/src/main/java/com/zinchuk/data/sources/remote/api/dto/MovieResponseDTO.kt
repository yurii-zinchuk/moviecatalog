package com.zinchuk.data.sources.remote.api.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
