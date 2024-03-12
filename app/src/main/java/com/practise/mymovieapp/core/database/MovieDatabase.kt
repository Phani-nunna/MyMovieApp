package com.practise.mymovieapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practise.mymovieapp.details.data.local.MovieDetailDao
import com.practise.mymovieapp.moviesList.data.local.movie.MovieDao

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val movieDetailDao: MovieDetailDao
}