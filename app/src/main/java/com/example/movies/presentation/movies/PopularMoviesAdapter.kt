package com.example.movies.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ListItemMovieBinding

class PopularMoviesAdapter :
    ListAdapter<PopularMoviesUiState.MoviesUiState, PopularMoviesAdapter.Holder>(
        PopularMovieUiStateItemCallback()
    ) {

    inner class Holder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.movie = getItem(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }
}