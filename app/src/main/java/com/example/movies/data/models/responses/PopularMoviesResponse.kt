package com.example.movies.data.models.responses

import com.example.movies.data.models.movie.PopularMovieDTO
import com.google.gson.annotations.SerializedName

class PopularMoviesResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val results: List<PopularMovieDTO>
): AppBaseResponse()