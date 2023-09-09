package com.example.movies.presentation.ui.details

import android.content.Context
import com.example.movies.presentation.base.BaseUiState
import com.example.movies.presentation.utils.ImagePathAttacher

data class MovieDetailsUiState(
    val id: Long,
    val title: String,
    private val rate: Double,
    private val imageUrl: String,
    val releaseDate: String,
    val description: String,
    val genre: List<GenreUiState>
) : BaseUiState() {

    fun getImageUrl(context: Context): String{
        return ImagePathAttacher.attachImageBaseUrl(context,imageUrl)
    }

    fun getRate(): String{
        return rate.toString()
    }

    data class GenreUiState(
        val name: String
    )
}