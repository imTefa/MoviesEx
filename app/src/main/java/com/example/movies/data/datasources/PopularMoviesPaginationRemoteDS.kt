package com.example.movies.data.datasources

import com.example.movies.data.base.BasePagingDataSource
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.models.movie.map
import com.example.movies.data.network.IoDispatcher
import com.example.movies.data.utils.resource.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PopularMoviesPaginationRemoteDS @Inject constructor(
    private val dataSource: MoviesRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BasePagingDataSource<PopularMovie>() {


    override suspend fun loadData(page: Int, size: Int): List<PopularMovie> {
        return withContext(dispatcher) {
            canGoNext = false
            dataSource.getMovies(page).let {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { response ->
                            canGoNext = response.totalPages > page
                            response.results.map { it.map() }
                        } ?: emptyList()
                    }

                    Status.ERROR -> throw Exception(it.errorMessage)
                    else -> throw Exception()
                }
            }
        }
    }
}