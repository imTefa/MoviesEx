package com.example.movies.domain.usecases.fake

import com.example.movies.data.models.movie.popular.PopularMovie

object FakeDS {
    val validIdForTest: Long = 615656
    val validMovieForTest =  PopularMovie(validIdForTest, "Meg 2: The Trench", 7.0, "2023", "")
    val invalidIdForTest: Long = 0L
    val fakeServerErrorMessage = "The resource you requested could not be found."

    val list: List<PopularMovie> = listOf(
        validMovieForTest,
        PopularMovie(346698, "Barbie", 7.4, "2023", ""),
        PopularMovie(335977, "Indiana Jones and the Dial of Destiny", 6.7, "2023", ""),
        PopularMovie(968051, "The Nun II", 7.0, "2023", ""),
        PopularMovie(912908, "Strays", 7.3, "2023", ""),
        PopularMovie(976573, "Elemental", 7.8, "2023", ""),
    )
    val commonOverView = "lerom absloum"
}