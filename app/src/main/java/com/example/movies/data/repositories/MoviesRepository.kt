package com.example.movies.data.repositories

import com.example.movies.data.models.movie.PopularMovie
import com.example.movies.data.models.movie.PopularMovieDTO
import com.example.movies.data.utils.resource.NetworkResource

interface MoviesRepository {

    suspend fun getPopularMovies(): NetworkResource<List<PopularMovie>>

}