package com.example.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.movies.data.models.movie.PopularMovie
import com.example.movies.data.models.movie.PopularMovieDTO
import com.example.movies.data.utils.resource.NetworkResource

interface MoviesRepository {

    suspend fun getPopularMovies(): NetworkResource<List<PopularMovie>>

    suspend fun getPopularMoviesPaged(): Pager<Int, PopularMovie>

}