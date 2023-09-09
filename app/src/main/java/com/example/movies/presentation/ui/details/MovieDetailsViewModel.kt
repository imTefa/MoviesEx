package com.example.movies.presentation.ui.details

import com.example.movies.data.utils.resource.Status
import com.example.movies.domain.usecases.MovieDetailsUseCase
import com.example.movies.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase
) : BaseViewModel<MovieDetailsUiState>() {

    override fun state(): Flow<MovieDetailsUiState> {
        return state.filterIsInstance()
    }


    fun loadMovieDetails(id: Long) {
        launchInViewModelScope {
            movieDetailsUseCase.invoke(id).collectLatest { result ->
                when (result.status) {
                    Status.LOADING -> triggerLoadingState()
                    Status.SUCCESS -> triggerUiState(DetailsUiStateMapper.fromModel(result.data!!))
                    Status.ERROR -> triggerErrorState(result.errorMessage)
                }
            }
        }
    }

}