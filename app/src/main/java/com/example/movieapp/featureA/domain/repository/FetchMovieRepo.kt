package com.example.movieapp.featureA.domain.repository

import com.example.movieapp.featureA.domain.model.Movie

interface FetchMovieRepo {
    suspend fun getMovies(): List<Movie>
}