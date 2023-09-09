package com.example.movies.data.network

import com.example.movies.data.models.movie.details.MovieDetailsDto
import com.example.movies.data.models.responses.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<PopularMoviesResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Long): Response<MovieDetailsDto>
}