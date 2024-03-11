package com.practise.mymovieapp.moviesList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practise.mymovieapp.moviesList.domain.repository.MovieListRepository
import com.practise.mymovieapp.moviesList.util.Category
import com.practise.mymovieapp.moviesList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {
    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)

                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }


            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        _movieListState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularMovieList->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList+ popularMovieList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage+1
                                )
                            }
                        }


                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }

            }
        }


    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        _movieListState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingMovieList->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList+ upcomingMovieList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage+1
                                )
                            }
                        }


                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }

            }
        }

    }
}