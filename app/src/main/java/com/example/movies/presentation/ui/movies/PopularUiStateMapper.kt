package com.example.movies.presentation.ui.movies

import com.example.movies.data.models.movie.popular.PopularMovie

object PopularUiStateMapper {

    fun fromModel(movie: PopularMovie): PopularMoviesUiState.MoviesUiState{
        return PopularMoviesUiState.MoviesUiState(
            id = movie.id,
            title = movie.title,
            rate = movie.rate,
            imageUrl = movie.image,
            releaseDate = movie.releaseDate
        )
    }
}