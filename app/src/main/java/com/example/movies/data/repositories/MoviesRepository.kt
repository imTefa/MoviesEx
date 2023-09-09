package com.example.movies.data.repositories

import androidx.paging.Pager
import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.utils.resource.NetworkResource

interface MoviesRepository {

    suspend fun getPopularMovies(): NetworkResource<List<PopularMovie>>

    suspend fun getPopularMoviesPaged(): Pager<Int, PopularMovie>

    suspend fun getMovieDetails(id: Long): NetworkResource<MovieDetails>

}