package com.example.movieapp.featureA.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.featureA.domain.model.Movie
import com.example.movieapp.featureA.domain.repository.FetchMovieRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(
    private val fetchMovieRepo: FetchMovieRepo
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<Movie>>(emptyList())
    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val movies = fetchMovieRepo.getMovies()
                _movieList.value = movies
            } catch (e: Exception) {
                Log.e("error movieviewmodel", "network error", e)
                _movieList.value = emptyList()
            }
        }
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
    }

    val filteredMovies = movieList
        .combine (searchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter { it.title.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList())
}
