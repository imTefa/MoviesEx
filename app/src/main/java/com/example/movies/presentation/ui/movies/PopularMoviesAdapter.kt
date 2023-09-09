package com.example.movies.presentation.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.ListItemMovieBinding

class PopularMoviesAdapter(private val listener: MovieClickListener) :
    PagingDataAdapter<PopularMoviesUiState.MoviesUiState, PopularMoviesAdapter.Holder>(
        PopularMovieUiStateItemCallback()
    ) {

    inner class Holder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)
            binding.movie = item
            binding.root.setOnClickListener {
                listener.onMovieItemClicked(
                    item?.id ?: return@setOnClickListener
                )
            }
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