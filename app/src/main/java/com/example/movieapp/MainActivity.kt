package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.movieapp.featureA.data.network.repository.FetchMovieRepoImpl
import com.example.movieapp.featureA.presentation.ui.MovieDetailScreen
import com.example.movieapp.featureA.presentation.ui.MovieScreen
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModel
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModelFactory
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repo    = FetchMovieRepoImpl()
        val factory = MovieViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        setContent {
            MovieAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController    = navController,
                        startDestination = "movieList"
                    ) {
                        composable("movieList") {
                            val movies by viewModel.filteredMovies.collectAsState()
                            val query  by viewModel.searchQuery.collectAsState()

                            MovieScreen(
                                movieList         = movies,
                                searchQuery       = query,
                                onSearch          = { viewModel.onSearch(it) },
                                onToggleFavourite = { viewModel.toggleFavourite(it) },
                                onMovieClick      = { m ->
                                    navController.navigate("movieDetail/${m.id}")
                                }
                            )
                        }

                        composable(
                            route     = "movieDetail/{movieId}",
                            arguments = listOf(navArgument("movieId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments!!.getInt("movieId")
                            MovieDetailScreen(
                                movieId   = movieId,
                                viewModel = viewModel,
                                onBack    = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
