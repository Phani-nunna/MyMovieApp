package com.practise.mymovieapp.details.data.repository

import com.practise.mymovieapp.core.database.MovieDatabase
import com.practise.mymovieapp.core.util.Category
import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.details.data.local.MovieDetailDao
import com.practise.mymovieapp.details.domain.repository.MovieDetailsRepository
import com.practise.mymovieapp.fake.Fake
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieDetailsRepositoryImplTest {

    private lateinit var movieDetailsRepository: MovieDetailsRepositoryImpl
    private var movieDatabase = mockk<MovieDatabase>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieDetailsRepository = MovieDetailsRepositoryImpl(movieDatabase)
    }

    @Test
    fun `test getMovie with existing movie`() = runTest {
        val movieDetailDao = mockk<MovieDetailDao>()
        val movieDatabase = mockk<MovieDatabase>()
        val movieEntity = Fake.movieEntityFake(Category.POPULAR)
        coEvery { movieDetailDao.getMovieById(1) } returns movieEntity
        coEvery { movieDatabase.movieDetailDao } returns movieDetailDao
        val repository = MovieDetailsRepositoryImpl(movieDatabase)
        val result = repository.getMovie(1)

        result.collectLatest {
            if (it is Resource.Success) {
                assert(it.data?.adult == false)
            }

        }

    }

    @Test
    fun `test getMovie with non-existing movie`() = runTest {

        val mockedObject = mockk<MovieDetailsRepository>()
        coEvery { mockedObject.getMovie(12) } returns flow { null }
        val res = mockedObject.getMovie(12)
        res.collect {
            if (it is Resource.Error) {
                assert(it.message == "Error No such movie")
            }
        }
        res.collect {
            if (it is Resource.Loading) {
                assert(!it.isLoading)
            }
        }
    }


}