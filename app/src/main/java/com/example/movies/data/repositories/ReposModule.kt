package com.example.movies.data.repositories

import com.example.movies.data.datasources.MoviesRemoteDataSource
import com.example.movies.data.datasources.PopularMoviesPaginationRemoteDS
import com.example.movies.data.network.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object ReposModule {

    @Provides
    fun provideMoviesRepo(
        dataSource: MoviesRemoteDataSource,
        pagingDataSource: PopularMoviesPaginationRemoteDS,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): MoviesRepository {
        return MoviesRepositoryImpl(dataSource, pagingDataSource, dispatcher)
    }


}