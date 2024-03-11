package com.practise.mymovieapp.details.presentation

import com.practise.mymovieapp.moviesList.domain.model.Movie

data class DetailsState(
    val isLoading:Boolean = false,
    val movie: Movie? = null
)
