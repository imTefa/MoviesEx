package com.example.movies.presentation.movies

import com.example.movies.data.models.movie.PopularMovie
import com.example.movies.presentation.base.BaseUiState

data class PopularMoviesUiState(
    val list: List<MoviesUiState>
) : BaseUiState() {


    data class MoviesUiState(
        val id: Long,
        val title: String,
        val rate: Double,
        val imageUrl: String,
        val releaseDate: String
    ) {

        companion object {
            fun fromMovie(movie: PopularMovie): MoviesUiState {
                return MoviesUiState(
                    id = movie.id,
                    title = movie.title,
                    rate = movie.rate,
                    imageUrl = movie.image,
                    releaseDate = movie.releaseDate
                )
            }
        }
    }
}