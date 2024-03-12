package com.practise.mymovieapp.moviesList.domain.repository

import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.moviesList.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>


}