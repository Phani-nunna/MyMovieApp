import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.practise.mymovieapp.core.util.Category
import com.practise.mymovieapp.core.util.Resource
import com.practise.mymovieapp.fake.Fake
import com.practise.mymovieapp.moviesList.domain.repository.MovieListRepository
import com.practise.mymovieapp.moviesList.presentation.MovieListState
import com.practise.mymovieapp.moviesList.presentation.MovieListViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = MockKRule(this)
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieListRepository: MovieListRepository
    private lateinit var observer: Observer<MovieListState>
    private lateinit var movieListState: MovieListState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        movieListRepository = mockk()
        movieListState = mockk()
        observer = mockk(relaxed = true)

        coEvery { movieListRepository.getMovieList(any(), any(), any()) } returns flow {
            emit(
                Resource.Success(
                    listOf(Fake.fake(Category.POPULAR))
                )
            )
        }
        coEvery { movieListRepository.getMovieList(any(), any(), any()) } returns flow {
            emit(
                Resource.Success(
                    listOf(Fake.fake(Category.UPCOMING))
                )
            )
        }
        viewModel = MovieListViewModel(movieListRepository)
    }

    @After
    fun teardown() {
        clearAllMocks()
    }

    @Test
    fun `test  fetching popular movie list`() = runTest {
        every { movieListState.isLoading } returns true
        every { movieListState.popularMovieList } returns listOf(Fake.fake(Category.POPULAR))

        val mockedObject = mockk<MovieListViewModel>()
        coEvery { mockedObject.getPopularMovieList(true) } returns Unit

        mockedObject.getPopularMovieList(true)
        assert(movieListState.popularMovieList.size == 1)
        assert(movieListState.isLoading)

        verify {
            mockedObject.getPopularMovieList(true)
        }
    }

    @Test
    fun `test  fetching Upcoming movie list`() = runTest {
        every { movieListState.isLoading } returns true
        every { movieListState.upcomingMovieList } returns listOf(Fake.fake(Category.UPCOMING))

        val mockedObject = mockk<MovieListViewModel>()
        coEvery { mockedObject.getUpcomingMovieList(true) } returns Unit

        mockedObject.getUpcomingMovieList(true)

        viewModel.movieListState.test {
            cancelAndIgnoreRemainingEvents()
        }

        assert(movieListState.upcomingMovieList.size == 1)
        assert(movieListState.isLoading)
        verify {
            mockedObject.getUpcomingMovieList(true)
        }
    }

}
