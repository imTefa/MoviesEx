package com.example.movies.data.models.movie.details

data class MovieDetails(
    val id: Long,
    val title: String,
    val rate: Double,
    val releaseDate: String,
    val image: String,
    val overView: String,
    val genres: List<Genre>
)
