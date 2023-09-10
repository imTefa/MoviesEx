package com.example.movies.domain.usecases.fake

import androidx.paging.PagingData
import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.data.utils.resource.NetworkResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FakeMoviesRepository(private val dispatcher: CoroutineDispatcher) : MoviesRepository {



    override suspend fun getPopularMoviesPagedV2(): Flow<PagingData<PopularMovie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(id: Long): NetworkResource<MovieDetails> {
        return withContext(dispatcher) {
            delay(500)
            val movie = FakeDS.list.find { it.id == id }
            if (movie != null) NetworkResource.success(
                MovieDetails(
                    id = movie.id,
                    title = movie.title,
                    rate = movie.rate,
                    releaseDate = movie.releaseDate,
                    image = "",
                    overView = FakeDS.commonOverView,
                    genres = emptyList()
                )
            )
            else NetworkResource.error(FakeDS.fakeServerErrorMessage)
        }
    }
}