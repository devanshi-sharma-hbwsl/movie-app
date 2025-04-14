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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
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
                    .map { it.copy(isFavourite = it.isFavourite) }
                _movieList.value = movies
            } catch (e: Exception) {
                Log.e("MovieViewModel", "network error", e)
                _movieList.value = emptyList()
            }
        }
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavourite(target: Movie) {
        _movieList.value = _movieList.value.map { movie ->
            if (movie.id == target.id) {
                movie.copy(isFavourite = !movie.isFavourite)
            } else {
                movie
            }
        }
    }

    val filteredMovies: StateFlow<List<Movie>> = movieList
        .combine(searchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter { it.title.contains(query, ignoreCase = true) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
