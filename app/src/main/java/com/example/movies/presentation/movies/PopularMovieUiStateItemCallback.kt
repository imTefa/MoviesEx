package com.example.movies.presentation.movies

import androidx.recyclerview.widget.DiffUtil

class PopularMovieUiStateItemCallback :
    DiffUtil.ItemCallback<PopularMoviesUiState.MoviesUiState>() {
    override fun areItemsTheSame(
        oldItem: PopularMoviesUiState.MoviesUiState,
        newItem: PopularMoviesUiState.MoviesUiState
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PopularMoviesUiState.MoviesUiState,
        newItem: PopularMoviesUiState.MoviesUiState
    ): Boolean {
        return oldItem == newItem
    }
}