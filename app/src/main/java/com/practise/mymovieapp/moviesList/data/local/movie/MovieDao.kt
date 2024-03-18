package com.practise.mymovieapp.moviesList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.practise.mymovieapp.core.database.MovieEntity

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMoveList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE category=:category")
    suspend fun getMovieListByCategory(category: String): List<MovieEntity>
}
