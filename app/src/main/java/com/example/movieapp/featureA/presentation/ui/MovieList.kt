package com.example.movieapp.featureA.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.featureA.domain.model.Movie

@Composable
fun MovieScreen(
    movieList: List<Movie>,
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MOVIES APP!",
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

        Text (
            text="You entered: $query"
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(movieList.withIndex().toList()) { (index, movie) ->
                Text(
                    text = "${index + 1}. ${movie.title}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
