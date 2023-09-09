package com.example.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.data.datasources.MoviesRemoteDataSource
import com.example.movies.data.datasources.PopularMoviesPaginationRemoteDS
import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.map
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.utils.resource.NetworkResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val dataSource: MoviesRemoteDataSource,
    private val pagingDataSource: PopularMoviesPaginationRemoteDS,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {
    override suspend fun getPopularMovies(): NetworkResource<List<PopularMovie>> {
        return withContext(dispatcher) {
            dataSource.getMovies().map { res ->
                res.results.map { it.map() }
            }
        }
    }

    override suspend fun getPopularMoviesPaged(): Pager<Int, PopularMovie> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = { pagingDataSource.createPagingSource() }
        )
    }

    override suspend fun getMovieDetails(id: Long): NetworkResource<MovieDetails> {
        return withContext(dispatcher) {
            dataSource.getMovieDetails(id).map { it.map() }
        }
    }
}