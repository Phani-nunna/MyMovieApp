package com.practise.mymovieapp.details.data.local

import androidx.room.Dao
import androidx.room.Query
import com.practise.mymovieapp.core.database.MovieEntity

@Dao
fun interface MovieDetailDao {
    @Query("SELECT * FROM MovieEntity WHERE id=:id")
    suspend fun getMovieById(id: Int): MovieEntity
}