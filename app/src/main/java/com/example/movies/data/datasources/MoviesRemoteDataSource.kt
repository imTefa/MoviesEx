package com.example.movies.data.datasources

import com.example.movies.data.models.responses.PopularMoviesResponse
import com.example.movies.data.network.Api
import com.example.movies.data.network.handleRetrofitApiCall
import com.example.movies.data.utils.resource.NetworkResource
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val api: Api
) {

    suspend fun getMovies(): NetworkResource<PopularMoviesResponse> {
        return handleRetrofitApiCall {
            api.getPopularMovies()
        }
    }
}