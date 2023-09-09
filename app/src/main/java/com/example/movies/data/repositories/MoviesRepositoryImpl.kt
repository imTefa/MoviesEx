package com.example.movies.data.repositories

import com.example.movies.data.datasources.MoviesRemoteDataSource
import com.example.movies.data.models.movie.PopularMovie
import com.example.movies.data.models.movie.map
import com.example.movies.data.utils.resource.NetworkResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val dataSource: MoviesRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {
    override suspend fun getPopularMovies(): NetworkResource<List<PopularMovie>> {
        return withContext(dispatcher) {
            dataSource.getMovies().map { res ->
                res.results.map { it.map() }
            }
        }
    }
}