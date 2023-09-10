package com.example.movies.domain.usecases

import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.data.utils.resource.Status
import com.example.movies.domain.usecases.fake.FakeDS
import com.example.movies.domain.usecases.fake.FakeMoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieDetailsUseCaseTest {

    private lateinit var dispatcher: CoroutineDispatcher
    private lateinit var repository: MoviesRepository
    private lateinit var useCase: MovieDetailsUseCase

    @Before
    fun before() = runTest {
        dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        repository = FakeMoviesRepository(dispatcher)
        useCase = MovieDetailsUseCase(repository, dispatcher)
    }

    @After
    fun after() = runTest {
        Dispatchers.resetMain()
    }

    @Test
    fun test_emit_loading_state_first() = runTest {
        val result = useCase.invoke(FakeDS.validIdForTest).first()
        Assert.assertEquals(Status.LOADING, result.status)
    }

    @Test
    fun test_emit_movie_when_passing_valid_id() = runTest {
        val results = useCase.invoke(FakeDS.validIdForTest).toList()
        Assert.assertEquals(Status.SUCCESS, results[1].status)
        Assert.assertEquals(FakeDS.validIdForTest, results[1].data?.id)
    }

    @Test
    fun test_emit_error_result_when_passing_invalid_id() = runTest {
        val results = useCase.invoke(FakeDS.invalidIdForTest).toList()
        Assert.assertEquals(Status.ERROR, results[1].status)
        Assert.assertEquals(FakeDS.fakeServerErrorMessage, results[1].errorMessage)
    }
}