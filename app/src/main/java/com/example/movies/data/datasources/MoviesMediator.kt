package com.example.movies.data.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.data.db.MoviesExDB
import com.example.movies.data.models.movie.map
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.models.movie.popular.PopularMovieEntity
import com.example.movies.data.models.movie.toEntity
import com.example.movies.data.models.remotekeys.RemoteKey
import com.example.movies.data.utils.resource.Status
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesMediator @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesExDB: MoviesExDB
) : RemoteMediator<Int, PopularMovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES)

        return if (System.currentTimeMillis() - (moviesExDB.getRemoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        return try {
            val result = moviesRemoteDataSource.getMovies(page)
            when (result.status) {
                Status.SUCCESS -> {
                    val movies = result.data!!.results
                    val canGoNext = result.data.totalPages > page
                    val endOfPagination = canGoNext.not()
                    moviesExDB.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            clearDB()
                        }

                        val prevKey = if (page > 1) page - 1 else null
                        val nextKey = if (endOfPagination) null else page + 1
                        val remoteKeys = movies.map {
                            RemoteKey(
                                movieID = it.id,
                                prevKey = prevKey,
                                currentPage = page,
                                nextKey = nextKey
                            )
                        }

                        moviesExDB.getRemoteKeysDao().insertAll(remoteKeys)
                        moviesExDB.getMoviesDao().insertAll(movies.map { movie ->
                            movie.map().toEntity(page)
                        })

                    }
                    return MediatorResult.Success(endOfPaginationReached = endOfPagination)
                }

                else -> {
                    throw Exception(result.errorMessage)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun clearDB(){
        moviesExDB.getRemoteKeysDao().clearRemoteKeys()
        moviesExDB.getMoviesDao().clearAllMovies()
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PopularMovieEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                moviesExDB.getRemoteKeysDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PopularMovieEntity>): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            moviesExDB.getRemoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PopularMovieEntity>): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            moviesExDB.getRemoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

}