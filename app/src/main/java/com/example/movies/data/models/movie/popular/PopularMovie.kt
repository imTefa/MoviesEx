package com.example.movies.data.models.movie.popular

data class PopularMovie(
  val id: Long,
  val title: String,
  val rate: Double,
  val releaseDate: String,
  val image: String,
)