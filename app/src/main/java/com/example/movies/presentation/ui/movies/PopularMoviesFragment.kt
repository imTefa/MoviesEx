package com.example.movies.presentation.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.movies.R
import com.example.movies.databinding.FragmentPopularMoviesBinding
import com.example.movies.presentation.base.BaseFragment
import com.example.movies.presentation.base.ErrorUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesFragment : BaseFragment<FragmentPopularMoviesBinding>(), MovieClickListener {

    private val viewModel by viewModels<PopularMoviesViewModel>()
    private val adapter = PopularMoviesAdapter(this)
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
                adapter.submitData(it.list)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->

                // Show loading spinner during initial load or refresh.
                showLoadingIndicator(loadState.source.refresh is LoadState.Loading)

                if (loadState.source.refresh is LoadState.Error) {
                    val error = (loadState.source.refresh as LoadState.Error).error
                    showErrorIndicator(ErrorUiState(error.localizedMessage))
                }

            }
        }

    }

    override fun onMovieItemClicked(id: Long) {
        findNavController().navigate(
            PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailsFragment(
                id
            )
        )
    }
}