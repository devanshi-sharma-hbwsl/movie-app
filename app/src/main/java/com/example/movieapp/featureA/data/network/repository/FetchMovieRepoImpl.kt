package com.example.movieapp.featureA.data.network.repository

import android.util.Log
import com.example.movieapp.featureA.data.api.RetrofitInstance
import com.example.movieapp.featureA.domain.model.Movie
import com.example.movieapp.featureA.domain.repository.FetchMovieRepo

class FetchMovieRepoImpl : FetchMovieRepo {
    override suspend fun getMovies(): List<Movie> {
        val temp2 = RetrofitInstance.api.getAPIMovies()

        try {
            val temp = RetrofitInstance.api.getAPIMovies()
            return temp
        }catch (e: Exception) {
            Log.e("error fetchmovierepoimpl", e.toString())
            return temp2
        }
    }
}
