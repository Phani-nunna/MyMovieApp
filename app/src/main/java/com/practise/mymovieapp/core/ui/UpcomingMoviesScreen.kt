package com.practise.mymovieapp.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.practise.mymovieapp.R
import com.practise.mymovieapp.core.ui.components.MovieItem
import com.practise.mymovieapp.core.util.Category
import com.practise.mymovieapp.moviesList.presentation.MovieListState
import com.practise.mymovieapp.moviesList.presentation.MovieListUiEvent

@Composable
fun UpcomingMoviesScreen(
    movieListState: MovieListState,
    navHostController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {
    if (movieListState.upcomingMovieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                vertical = dimensionResource(id = R.dimen.dp_8),
                horizontal = dimensionResource(id = R.dimen.dp_4)
            )

        ) {
            items(movieListState.upcomingMovieList.size) { index ->
                MovieItem(
                    movie = movieListState.upcomingMovieList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_16)))
                if (index >= movieListState.upcomingMovieList.size - 1 && !movieListState.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.UPCOMING))
                }
            }
        }
    }
}