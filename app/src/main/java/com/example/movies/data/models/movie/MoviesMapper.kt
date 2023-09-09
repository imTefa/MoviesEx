package com.example.movies.data.models.movie

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

