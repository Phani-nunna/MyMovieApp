package com.practise.mymovieapp.fake

import com.practise.mymovieapp.core.database.MovieEntity
import com.practise.mymovieapp.moviesList.data.remote.response.MovieDto
import com.practise.mymovieapp.moviesList.data.remote.response.MoviesListDto
import com.practise.mymovieapp.moviesList.domain.model.Movie

object Fake {
    fun fakeMovieDetail(category: String) = Movie(
        adult = false,
            backdrop_path= "/mDeUmPe4MF35WWlAqj4QFX5UauJ.jpg",
            genre_ids = listOf(
                28,
                27,
                53
            ),
            id=1096197,
            original_language= "pt",
            original_title= "No Way Up",
            overview= "Characters from different backgrounds are thrown together when the ",
            popularity= 1762.83,
            poster_path= "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
            release_date= "2024-01-18",
            title= "No Way Up",
            video= false,
            vote_average =6.055,
            vote_count=192,
            category = category)


    fun fakeMoviesListDto() = MoviesListDto(
        1,
        listOf(movieDto()),
        2,
        2
    )



        /**
         * Fake user for testing and UI preview
         */
        fun fake(category: String) = Movie(adult = false,
            backdrop_path= "/mDeUmPe4MF35WWlAqj4QFX5UauJ.jpg",
            genre_ids = listOf(
                28,
                27,
                53
            ),
            id=1096197,
            original_language= "pt",
            original_title= "No Way Up",
            overview= "Characters from different backgrounds are thrown together when the ",
            popularity= 1762.83,
            poster_path= "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
            release_date= "2024-01-18",
            title= "No Way Up",
            video= false,
            vote_average =6.055,
            vote_count=192,
            category = category)

    fun movieDto() = MovieDto(adult = false,
        backdrop_path= "/mDeUmPe4MF35WWlAqj4QFX5UauJ.jpg",
        genre_ids = listOf(
            28,
            27,
            53
        ),
        id=1096197,
        original_language= "pt",
        original_title= "No Way Up",
        overview= "Characters from different backgrounds are thrown together when the ",
        popularity= 1762.83,
        poster_path= "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
        release_date= "2024-01-18",
        title= "No Way Up",
        video= false,
        vote_average =6.055,
        vote_count=192)

    fun movieEntityFake(category: String) = MovieEntity(
        adult = false,
        backdrop_path= "/mDeUmPe4MF35WWlAqj4QFX5UauJ.jpg",
        genre_ids ="88",

        id=1096197,
        original_language= "pt",
        original_title= "No Way Up",
        overview= "Characters from different backgrounds are thrown together when the ",
        popularity= 1762.83,
        poster_path= "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
        release_date= "2024-01-18",
        title= "No Way Up",
        video= false,
        vote_average =6.055,
        vote_count=192,
        category = category

    )
}