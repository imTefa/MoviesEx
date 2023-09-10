package com.example.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.utils.resource.NetworkResource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMoviesPagedV2():  Flow<PagingData<PopularMovie>>

    suspend fun getMovieDetails(id: Long): NetworkResource<MovieDetails>

}