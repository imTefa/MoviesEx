package com.example.movies.data.models.movie

data class PopularMovie(
  val id: Long,
  val title: String,
  val rate: Double,
  val releaseDate: String,
  val image: String,
  val overView: String
)