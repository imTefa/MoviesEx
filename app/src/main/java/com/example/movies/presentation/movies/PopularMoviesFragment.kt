package com.example.movies.presentation.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movies.R
import com.example.movies.databinding.FragmentPopularMoviesBinding
import com.example.movies.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<FragmentPopularMoviesBinding>() {

    private val viewModel by viewModels<PopularMoviesViewModel>()
    private val adapter = PopularMoviesAdapter()
    override val layout: Int
        get() = R.layout.fragment_popular_movies


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPopularMovies()
        binding.list.adapter = adapter
    }

    override fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest {
                adapter.submitList(it.list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorUiState().collectLatest {
                showErrorIndicator(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState().collectLatest {
                showLoadingIndicator(it)
            }
        }

    }
}