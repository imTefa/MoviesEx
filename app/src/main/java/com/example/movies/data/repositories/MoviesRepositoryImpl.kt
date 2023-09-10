package com.example.movies.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.datasources.MoviesMediator
import com.example.movies.data.datasources.MoviesRemoteDataSource
import com.example.movies.data.datasources.PopularMoviesPaginationRemoteDS
import com.example.movies.data.db.MoviesDao
import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.map
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.models.movie.toModel
import com.example.movies.data.utils.resource.NetworkResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val dataSource: MoviesRemoteDataSource,
    private val pagingDataSource: PopularMoviesPaginationRemoteDS,
    private val moviesDao: MoviesDao,
    private val moviesMediator: MoviesMediator,
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

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPopularMoviesPagedV2(): Flow<PagingData<PopularMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                moviesDao.getMovies()
            },
            remoteMediator = moviesMediator
        ).flow.map {
            it.map { movie ->
                movie.toModel()
            }
        }
    }

    override suspend fun getMovieDetails(id: Long): NetworkResource<MovieDetails> {
        return withContext(dispatcher) {
            dataSource.getMovieDetails(id).map { it.map() }
        }
    }
}