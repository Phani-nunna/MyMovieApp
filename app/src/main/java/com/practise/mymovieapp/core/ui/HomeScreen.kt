package com.practise.mymovieapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.practise.mymovieapp.R
import com.practise.mymovieapp.moviesList.presentation.MovieListUiEvent
import com.practise.mymovieapp.moviesList.presentation.MovieListViewModel
import com.practise.mymovieapp.core.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieListState = movieListViewModel.movieListState.collectAsStateWithLifecycle().value
    val bottomNavHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavHostController = bottomNavHostController,
                onEvent = movieListViewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieListState.isCurrentPopularScreen)
                            stringResource(id = R.string.popular_movies)
                        else
                            stringResource(id = R.string.upcoming_movies),
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(dimensionResource(id = R.dimen.dp_2)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface
                ),
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavHostController,
                startDestination = Screen.PopularMovieList.rout
            ) {
                composable(Screen.PopularMovieList.rout) {
                    PopularMovieScreen(
                        movieListState = movieListState,
                        navHostController = navController,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovieList.rout) {
                    UpcomingMoviesScreen(
                        movieListState = movieListState,
                        navHostController = navController,
                        onEvent = movieListViewModel::onEvent
                    )
                }

            }
        }

    }
}

@Composable
fun BottomNavigationBar(
    bottomNavHostController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = stringResource(R.string.popular),
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = stringResource(R.string.upcoming),
            icon = Icons.Rounded.Upcoming
        )
    )
    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavHostController.popBackStack()
                                bottomNavHostController.navigate(Screen.PopularMovieList.rout)
                            }

                            1 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavHostController.popBackStack()
                                bottomNavHostController.navigate(Screen.UpcomingMovieList.rout)
                            }
                        }

                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground

                        )
                    }
                )

            }
        }

    }
}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)