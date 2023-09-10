package com.example.movies.presentation.ui.details

import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.domain.usecases.MovieDetailsUseCase
import com.example.movies.domain.usecases.fake.FakeDS
import com.example.movies.domain.usecases.fake.FakeMoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieDetailsViewModelTest {

    private lateinit var dispatcher: CoroutineDispatcher
    private lateinit var repository: MoviesRepository
    private lateinit var useCase: MovieDetailsUseCase
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun before() = runTest {
        dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        repository = FakeMoviesRepository(dispatcher)
        useCase = MovieDetailsUseCase(repository, dispatcher)
        viewModel = MovieDetailsViewModel(useCase)
    }

    @After
    fun after() = runTest {
        Dispatchers.resetMain()
    }

    @Test
    fun test_trigger_loading_state_until_load_details_state() = runTest {
        val states = mutableListOf<Boolean>()
        backgroundScope.launch {
            viewModel.loadingState().collectLatest {
                states.add(it)
            }
        }
        viewModel.loadMovieDetails(FakeDS.validIdForTest)
        delay(1000)
        Assert.assertEquals(true, states[0])
        Assert.assertEquals(false, states[1])
    }

    @Test
    fun test_trigger_error_state_when_passing_invalid_id() = runTest {
        viewModel.loadMovieDetails(FakeDS.invalidIdForTest)
        val errorMessage = viewModel.errorUiState().first().errorMessage
        Assert.assertEquals(FakeDS.fakeServerErrorMessage, errorMessage)
    }

    @Test
    fun test_trigger_details_ui_state_when_passing_valid_id() = runTest {
        viewModel.loadMovieDetails(FakeDS.validIdForTest)
        val movieUiState = viewModel.state().first()
        Assert.assertEquals(FakeDS.validMovieForTest.id, movieUiState.id)
    }

}