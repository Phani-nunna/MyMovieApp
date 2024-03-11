package com.practise.mymovieapp.moviesList.data.remote

import com.practise.mymovieapp.moviesList.data.remote.response.MoviesListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apikey: String = API_KEY
    ):MoviesListDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "25ce3f99c08f2bb5003c44fae85202b9"
    }
}