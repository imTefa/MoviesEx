package com.example.movies.data.network

import retrofit2.http.GET

interface Api {

    @GET("3/movie/popular")
    fun getPopularMovies()
}