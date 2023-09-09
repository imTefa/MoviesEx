package com.example.movies.presentation.ui.details

import com.example.movies.data.models.movie.details.MovieDetails

object DetailsUiStateMapper {

    fun fromModel(movie: MovieDetails): MovieDetailsUiState {
        return MovieDetailsUiState(
            id = movie.id,
            title = movie.title,
            rate = movie.rate,
            imageUrl = movie.image,
            releaseDate = movie.releaseDate,
            description = movie.overView,
            genre = movie.genres.map { MovieDetailsUiState.GenreUiState(it.name) }
        )
    }
}
