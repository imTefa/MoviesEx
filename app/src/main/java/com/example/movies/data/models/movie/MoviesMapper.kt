package com.example.movies.data.models.movie

import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.details.MovieDetailsDto
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.models.movie.popular.PopularMovieDTO

fun PopularMovieDTO.map(): PopularMovie {
    return PopularMovie(
        id = this.id,
        title = title,
        rate = voteAverage,
        releaseDate = releaseDate,
        image = posterPath,
        overView = overview
    )
}

fun MovieDetailsDto.map(): MovieDetails{
    return MovieDetails(
        id = id,
        title = title,
        rate = voteAverage,
        releaseDate = releaseDate,
        image = posterPath,
        overView = overview,
        genres = genres
    )
}
