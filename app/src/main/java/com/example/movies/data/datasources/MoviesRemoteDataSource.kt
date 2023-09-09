package com.example.movies.data.datasources

import com.example.movies.data.models.movie.details.MovieDetailsDto
import com.example.movies.data.models.responses.PopularMoviesResponse
import com.example.movies.data.network.Api
import com.example.movies.data.network.handleRetrofitApiCall
import com.example.movies.data.utils.resource.NetworkResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRemoteDataSource @Inject constructor(
    private val api: Api
) {

    suspend fun getMovies(page: Int = 1): NetworkResource<PopularMoviesResponse> {
        return handleRetrofitApiCall {
            api.getPopularMovies(page)
        }
    }

    suspend fun getMovieDetails(id: Long): NetworkResource<MovieDetailsDto>{
        return handleRetrofitApiCall {
            api.getMovieDetails(id)
        }
    }
}