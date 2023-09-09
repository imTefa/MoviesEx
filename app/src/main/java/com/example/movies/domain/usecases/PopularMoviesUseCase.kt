package com.example.movies.domain.usecases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.network.IoDispatcher
import com.example.movies.data.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(scope: CoroutineScope): Flow<PagingData<PopularMovie>>{
        return moviesRepository.getPopularMoviesPaged().flow.flowOn(dispatcher).cachedIn(scope)
    }

}