package com.practise.mymovieapp.moviesList.data.remote.response


data class MoviesListDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)