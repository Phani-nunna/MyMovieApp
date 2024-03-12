package com.practise.mymovieapp.details.presentation

import com.practise.mymovieapp.details.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
