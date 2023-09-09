package com.example.movies.presentation.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movies.R
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.presentation.base.BaseFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {

    override val layout: Int
        get() = R.layout.fragment_movie_details

    private val viewModel by viewModels<MovieDetailsViewModel>()
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMovieDetails(args.id)
        initNavigationClickListener()
    }

    private fun initNavigationClickListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState().collectLatest { showLoadingIndicator(it) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorUiState().collectLatest { showErrorIndicator(it) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest {
                binding.movie = it
                it.genre.forEach { genre ->
                    binding.chipGroup.addView(Chip(requireContext()).apply {
                        text = genre.name
                    })
                }
            }
        }
    }
}