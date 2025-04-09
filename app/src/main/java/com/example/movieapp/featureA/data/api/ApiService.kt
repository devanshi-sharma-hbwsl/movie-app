package com.example.movieapp.featureA.data.api

import com.example.movieapp.featureA.domain.model.Movie
import retrofit2.http.GET

interface ApiService {
    @GET("futurama/episodes")
    suspend fun getAPIMovies(): List<Movie>
}