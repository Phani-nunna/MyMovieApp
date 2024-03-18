package com.practise.mymovieapp.di

import com.practise.mymovieapp.details.data.repository.MovieDetailsRepositoryImpl
import com.practise.mymovieapp.details.domain.repository.MovieDetailsRepository
import com.practise.mymovieapp.moviesList.data.repository.MovieListRepositoryImpl
import com.practise.mymovieapp.moviesList.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository

    @Binds
    @Singleton
    abstract fun bindMovieDetailRepository(
        movieDetailRepositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository
}