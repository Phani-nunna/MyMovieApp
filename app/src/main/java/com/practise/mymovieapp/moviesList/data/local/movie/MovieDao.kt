package com.practise.mymovieapp.moviesList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao{

  @Upsert
 suspend fun upsertMoveList(movieList:List<MovieEntity>)

 @Query("SELECT * FROM MovieEntity WHERE id=:id")
 suspend fun getMovieById(id:Int):MovieEntity

 
    @Query("SELECT * FROM MovieEntity WHERE category=:category")
    suspend fun getMovieListByCategeory(category:String):List<MovieEntity>
}
