package com.example.movies.data.models.movie

import com.example.movies.data.models.movie.details.MovieDetails
import com.example.movies.data.models.movie.details.MovieDetailsDto
import com.example.movies.data.models.movie.popular.PopularMovie
import com.example.movies.data.models.movie.popular.PopularMovieDTO
import com.example.movies.data.models.movie.popular.PopularMovieEntity

fun PopularMovieDTO.map(): PopularMovie {
    return PopularMovie(
        id = this.id,
        title = title,
        rate = voteAverage,
        releaseDate = releaseDate,
        image = posterPath,
    )
}

fun PopularMovie.toEntity(page: Int = 0): PopularMovieEntity {
    return PopularMovieEntity(
        id = id,
        title = title,
        rate = rate,
        releaseDate = releaseDate,
        image = image,
        page = page
    )
}

fun PopularMovieEntity.toModel(): PopularMovie {
    return PopularMovie(
        id = id,
        title = title,
        rate = rate,
        releaseDate = releaseDate,
        image = image
    )
}

fun MovieDetailsDto.map(): MovieDetails {
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
