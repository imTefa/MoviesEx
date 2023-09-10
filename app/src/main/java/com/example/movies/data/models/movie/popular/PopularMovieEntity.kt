package com.example.movies.data.models.movie.popular

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class PopularMovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val rate: Double,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val image: String,
    @ColumnInfo(name = "page")
    var page: Int,
)