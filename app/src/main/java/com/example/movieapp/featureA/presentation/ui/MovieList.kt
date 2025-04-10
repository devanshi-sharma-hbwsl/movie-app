package com.example.movieapp.featureA.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movieapp.featureA.domain.model.Movie

@Composable
fun MovieScreen(
    movieList: List<Movie>,
    onSearch: (String) -> Unit,
    onToggleFavourite: (Movie) -> Unit
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MOVIES APP!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                onSearch(it)
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(movieList) { index, movie ->
                MovieRow(movie = movie, index = index, onToggleFavourite = { onToggleFavourite(it) })
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, index: Int, onToggleFavourite: (Movie) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${index + 1}. ${movie.title}",
            style = MaterialTheme.typography.bodyLarge
        )

        Icon(
            imageVector = if (movie.isFavourite) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = "Favourite Icon",
            tint = if (movie.isFavourite) Color.Magenta else Color.Gray,
            modifier = Modifier
                .size(24.dp)
                .clickable { onToggleFavourite(movie) }
        )
    }
}
