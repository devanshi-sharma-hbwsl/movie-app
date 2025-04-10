package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.featureA.presentation.ui.MovieScreen
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.featureA.data.network.repository.FetchMovieRepoImpl
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModel
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
//                                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                    Greeting(
//                                        name = "Devanshi",
//                                        modifier = Modifier.padding(innerPadding)
//                                    )
//                                }

                val repo = FetchMovieRepoImpl()
                val factory = MovieViewModelFactory(repo)
                val viewModel: MovieViewModel = viewModel(factory = factory)

                val movieList by viewModel.filteredMovies.collectAsState()

                Log.d("testing", movieList.toString())

                Surface(color = MaterialTheme.colorScheme.background) {
                    MovieScreen(
                        movieList = movieList,
                        onSearch = { viewModel.onSearch(it) },
                        onToggleFavourite = { viewModel.toggleFavourite(it) }
                    )
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun TestPreview() {
        MovieAppTheme {
            Greeting("devanshiiiiii")
        }
    }
}