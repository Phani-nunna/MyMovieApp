package com.practise.mymovieapp.details.data.repository

import com.practise.mymovieapp.core.database.MovieDatabase
import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.details.data.mappers.toMovie
import com.practise.mymovieapp.details.domain.model.Movie
import com.practise.mymovieapp.details.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieDatabase: MovieDatabase
) : MovieDetailsRepository {
    override  fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val movieEntity = movieDatabase.movieDetailDao.getMovieById(id)
            if (null != movieEntity) {
                emit(
                    Resource.Success(
                        movieEntity.toMovie(movieEntity.category)
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error("Error No such movie "))
            emit(Resource.Loading(false))

        }
            .onEmpty {
            emit(Resource.Error("Error No such movie"))
            emit(Resource.Loading(false))
        }
    }

}