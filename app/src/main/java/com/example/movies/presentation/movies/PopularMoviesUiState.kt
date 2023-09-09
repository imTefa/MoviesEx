package com.example.movies.presentation.movies

import android.content.Context
import androidx.paging.PagingData
import com.example.movies.R
import com.example.movies.data.models.movie.PopularMovie
import com.example.movies.presentation.base.BaseUiState

data class PopularMoviesUiState(
    val list: PagingData<MoviesUiState>
) : BaseUiState() {


    data class MoviesUiState(
        val id: Long,
        val title: String,
        private val rate: Double,
        private val imageUrl: String,
        val releaseDate: String
    ) {

        fun getImageUrl(context: Context): String{
            return context.getString(R.string.image_url) + imageUrl
        }

        fun getRate(): String {
            return rate.toString()
        }

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