package com.practise.mymovieapp.moviesList.data.repository

import com.practise.mymovieapp.core.database.MovieDatabase
import com.practise.mymovieapp.core.util.Category
import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.fake.Fake
import com.practise.mymovieapp.moviesList.data.remote.MovieApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MovieListRepositoryImplTest {

    private val apiService = mockk<MovieApi>(relaxed = true)
    lateinit var movieListRepository: MovieListRepositoryImpl
    private var movieDatabase = mockk<MovieDatabase>(relaxed = true)
    private val movie = Fake.fakeMoviesListDto()

    @Before
    public fun setUp() {
        MockKAnnotations.init(this)
        movieListRepository = MovieListRepositoryImpl(apiService, movieDatabase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMovieList() = runTest {
        coEvery { apiService.getMoviesList(any(), any(), any()) } returns movie
        coEvery { movieDatabase.movieDao.getMovieListByCategeory(Category.POPULAR) } returns listOf(
            Fake.movieEntityFake(Category.POPULAR)
        )
        // Invoke
        val result = movieListRepository.getMovieList(false, Category.POPULAR, 1)
        result.collect {
            if (it is Resource.Success) {
                assert(it.data?.get(0)?.adult == false)
                assert(it.data?.get(0)?.id == 1096197)
            }
        }
    }

    @Test
    fun getMovieListFromApi() = runTest {
        coEvery { apiService.getMoviesList(any(), any(), any()) } returns movie
        // Invoke
        val result = movieListRepository.getMovieList(true, Category.POPULAR, 1)
        result.collect {
            if (it is Resource.Success) {
                assert(it.data?.get(0)?.adult == false)
                assert(it.data?.get(0)?.id == 1096197)
            }
        }
    }

    @Test
    fun `test getMovieList with exception`() = runTest {
        coEvery {
            apiService.getMoviesList(
                any(), any(), any()
            )
        } throws IOException("Mocked exception")
        val result = movieListRepository.getMovieList(true, Category.POPULAR, 1)
        // assertEquals(Resource.Error("Error loading movies"), (result[1] as Resource.Error).message) // Second emission should be error state with the correct message
        result.collectLatest {
            if (it is Resource.Error) {
                assert(it.message == "Error loading movies")
            }

        }

    }

    @Test
    fun `test getMovieList with Exception`() = runTest {
        coEvery { apiService.getMoviesList(any(), any(), any()) } throws Exception()
        val result = movieListRepository.getMovieList(true, Category.POPULAR, 1)
        // assertEquals(Resource.Error("Error loading movies"), (result[1] as Resource.Error).message) // Second emission should be error state with the correct message
        result.collectLatest {
            if (it is Resource.Error) {
                assert(it.message == "Error loading movies")
            }

        }

    }

}