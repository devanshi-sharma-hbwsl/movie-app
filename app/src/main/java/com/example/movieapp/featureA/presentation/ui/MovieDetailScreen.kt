package com.example.movieapp.featureA.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.featureA.presentation.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieViewModel,
    onBack: () -> Unit
) {
    val movie by remember {
        derivedStateOf {
            viewModel.movieList.value.first { it.id == movieId }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Description:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.desc,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Writers:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.writers,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Original Air Date:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.originalAirDate,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Number:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.number,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
