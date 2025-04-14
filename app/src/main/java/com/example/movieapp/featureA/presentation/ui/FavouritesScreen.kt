package com.example.movieapp.featureA.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.featureA.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    favouriteList: List<Movie>,
    onToggleFavourite: (Movie) -> Unit,
    onMovieClick: (Movie) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favourites") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (favouriteList.isEmpty()) {
                Text("No favourites yet.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn {
                    itemsIndexed(favouriteList) { index, movie ->
                        MovieRow(
                            movie             = movie,
                            index             = index,
                            onToggleFavourite = onToggleFavourite,
                            onClick           = onMovieClick
                        )
                    }
                }
            }
        }
    }
}
