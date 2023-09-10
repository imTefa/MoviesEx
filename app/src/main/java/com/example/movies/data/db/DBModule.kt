package com.example.movies.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): MoviesExDB =
        Room.databaseBuilder(context, MoviesExDB::class.java, "movies_database")
            .build()

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesExDB): MoviesDao = moviesDatabase.getMoviesDao()

    @Provides
    fun provideRemoteKeysDao(moviesDatabase: MoviesExDB): RemoteKeysDao =
        moviesDatabase.getRemoteKeysDao()
}