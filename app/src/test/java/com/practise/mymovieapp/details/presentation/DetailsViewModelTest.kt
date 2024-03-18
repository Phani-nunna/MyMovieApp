package com.practise.mymovieapp.details.presentation

import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.practise.mymovieapp.core.util.Category
import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.details.domain.repository.MovieDetailsRepository
import com.practise.mymovieapp.fake.Fake
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = MockKRule(this)
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailsViewModel
    var movieDetailsRepository: MovieDetailsRepository = mockk(relaxed = true)
    private lateinit var observer: Observer<DetailsState>
    private lateinit var detailsState: DetailsState
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun start() {
        savedStateHandle = mockk()
        Dispatchers.setMain(dispatcher)
        //  movieDetailsRepository = mockk()
        detailsState = mockk()
        observer = mockk(relaxed = true)
        every { savedStateHandle.get<Int>("movieId") } returns 12


        viewModel = DetailsViewModel(movieDetailsRepository, savedStateHandle)

    }

    @Test
    fun getMovieDetails() = runTest {
        coEvery { movieDetailsRepository.getMovie(12) } returns flow {
            Resource.Success(
                Fake.fakeMovieDetail(
                    Category.POPULAR
                )
            )
        }

        val res = movieDetailsRepository.getMovie(12)
        res.collect {
            if (it is Resource.Success) {
                assert(it.data?.adult == false)
            }
        }


    }

    @Test
    fun getMovieDetailsError() = runTest {

        coEvery { movieDetailsRepository.getMovie(0) } returns flow { emit(Resource.Error("Error No such movie")) }

        val res = movieDetailsRepository.getMovie(0)
        res.collect {
            if (it is Resource.Error) {
                assert(it.message == "Error No such movie")
            }
        }


    }
}