package com.example.movies.presentation.ui.movies

import androidx.paging.map
import com.example.movies.domain.usecases.PopularMoviesUseCase
import com.example.movies.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase
) : BaseViewModel<PopularMoviesUiState>() {

    override fun state(): Flow<PopularMoviesUiState> {
        return state.filterIsInstance()
    }


    fun loadPopularMovies() {
        launchInViewModelScope {scope->
            popularMoviesUseCase.invoke(scope).collectLatest {
                triggerUiState(PopularMoviesUiState(
                    list = it.map { movie ->
                        PopularUiStateMapper.fromModel(movie)
                    }
                ))
            }
        }
    }
}