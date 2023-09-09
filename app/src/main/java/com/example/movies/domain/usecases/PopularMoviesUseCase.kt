package com.example.movies.domain.usecases

import androidx.paging.PagingData
import com.example.movies.data.models.movie.PopularMovie
import com.example.movies.data.models.movie.PopularMovieDTO
import com.example.movies.data.network.IoDispatcher
import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.data.utils.resource.NetworkResource
import com.example.movies.data.utils.startWithLoadingResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Flow<PagingData<PopularMovie>>{
        return moviesRepository.getPopularMoviesPaged().flow.flowOn(dispatcher)
    }

}