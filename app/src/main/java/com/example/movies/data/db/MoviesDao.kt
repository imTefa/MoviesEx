package com.example.movies.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.models.movie.popular.PopularMovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<PopularMovieEntity>)

    @Query("Select * From movies Order By page")
    fun getMovies(): PagingSource<Int, PopularMovieEntity>

    @Query("Delete From movies")
    suspend fun clearAllMovies()
}