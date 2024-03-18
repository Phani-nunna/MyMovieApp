package com.practise.mymovieapp.details.domain.repository

import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.details.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
     fun getMovie(id: Int): Flow<Resource<Movie>>
}