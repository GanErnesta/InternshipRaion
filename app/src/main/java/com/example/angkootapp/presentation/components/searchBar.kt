package com.example.angkootapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val suggestions = listOf(
        "Terminal Arjosari",
        "Terminal Landungsari",
        "Terminal Hamid Rusdi",
    )

    val filteredSuggestions = suggestions.filter {
        it.contains(text, ignoreCase = true)
    }

    Box(modifier = modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)) {
        DockedSearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                onSearch(it)
                active = false
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Cari terminal...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (active && text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            content = {
                filteredSuggestions.forEach { suggestion ->
                    ListItem(
                        headlineContent = { Text(suggestion) },
                        leadingContent = { Icon(Icons.Default.Place, contentDescription = null) },
                        modifier = Modifier.clickable {
                            text = suggestion
                            active = false
                            onSearch(suggestion)
                        }
                    )
                }
            }
        )
    }
}