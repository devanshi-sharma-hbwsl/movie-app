package com.example.movieapp.featureA.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.featureA.domain.repository.FetchMovieRepo

class MovieViewModelFactory(
    private val repo: FetchMovieRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(repo) as T
    }
}
