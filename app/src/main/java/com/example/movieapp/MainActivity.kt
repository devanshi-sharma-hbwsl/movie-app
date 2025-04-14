package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.movieapp.featureA.data.network.repository.FetchMovieRepoImpl
import com.example.movieapp.featureA.presentation.ui.FavouritesScreen
import com.example.movieapp.featureA.presentation.ui.MovieDetailScreen
import com.example.movieapp.featureA.presentation.ui.MovieScreen
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModel
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModelFactory
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                                },
                                onFavouritesClick = {
                                    navController.navigate("favourites")
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

                        composable("favourites") {
                            val allMovies by viewModel.movieList.collectAsState()
                            val favourites = allMovies.filter { it.isFavourite }

                            FavouritesScreen(
                                favouriteList     = favourites,
                                onToggleFavourite = { viewModel.toggleFavourite(it) },
                                onMovieClick      = { m ->
                                    navController.navigate("movieDetail/${m.id}")
                                },
                                onBack            = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
