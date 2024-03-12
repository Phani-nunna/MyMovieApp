package com.practise.mymovieapp.moviesList.data.repository

import com.practise.mymovieapp.core.database.MovieDatabase
import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.moviesList.data.mappers.toMovie
import com.practise.mymovieapp.moviesList.data.mappers.toMovieEntity
import com.practise.mymovieapp.moviesList.data.remote.MovieApi
import com.practise.mymovieapp.moviesList.domain.model.Movie
import com.practise.mymovieapp.moviesList.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(isLoading = true))
            val localMoviesList = movieDatabase.movieDao.getMovieListByCategeory(category)
            val shouldLoadLocalMovie = localMoviesList.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalMovie) {
                emit(
                    Resource.Success(
                        data = localMoviesList.map { movieEntity ->
                            movieEntity.toMovie(category)

                        }
                    ))
                emit(Resource.Loading(false))
                return@flow
            }

            val moviesListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                return@flow
            }

            val movieEntities = moviesListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)

                }
            }
            movieDatabase.movieDao.upsertMoveList(movieEntities)
            emit(
                Resource.Success(
                    movieEntities.map {
                        it.toMovie(category)
                    }
                ))
            emit(Resource.Loading(false))

        }

    }


}