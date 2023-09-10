package com.example.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.models.movie.popular.PopularMovieEntity
import com.example.movies.data.models.remotekeys.RemoteKey

@Database(
    entities = [PopularMovieEntity::class, RemoteKey::class],
    version = 1,
)
abstract class MoviesExDB: RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao

}