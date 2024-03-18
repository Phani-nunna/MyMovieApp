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
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailsRepositoryImplTest {

    lateinit var movieDetailsRepository: MovieDetailsRepositoryImpl
    private var movieDatabase = mockk<MovieDatabase>(relaxed = true)

    @Before
    public fun setUp() {
        MockKAnnotations.init(this)
        movieDetailsRepository = MovieDetailsRepositoryImpl(movieDatabase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test getMovie with existing movie`() = runTest {
        // Mock dependencies
        val movieDetailDao = mockk<MovieDetailDao>()
        val movieDatabase = mockk<MovieDatabase>()

        // Mock movie entity
        val movieEntity = Fake.movieEntityFake(Category.POPULAR)

        // Mock database response
        coEvery { movieDetailDao.getMovieById(1) } returns movieEntity
        coEvery { movieDatabase.movieDetailDao } returns movieDetailDao

        // Instantiate the class under test
        val repository = MovieDetailsRepositoryImpl(movieDatabase)

        // Run the test
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
                assert(it.isLoading == false)
            }
        }
    }


}