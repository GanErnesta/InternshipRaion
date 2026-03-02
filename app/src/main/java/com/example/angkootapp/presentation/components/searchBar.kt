package com.example.angkootapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar(
    modifier: Modifier = Modifier,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    val suggestions = listOf(
        "Terminal Arjosari",
        "Terminal Landungsari",
        "Terminal Hamid Rusdi",
        "Terminal Arjosari",
        "Terminal Landungsari",
        "Terminal Hamid Rusdi"
    )

    val filteredSuggestions = suggestions.filter {
        it.contains(text, ignoreCase = true)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 15.dp, shape = SearchBarDefaults.dockedShape, clip = false),
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                onSearch(it)
                onActiveChange(false)
            },
            active = active,
            onActiveChange = onActiveChange,
            placeholder = { Text("Cari terminal / Angkot...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            trailingIcon = {
                if (active && text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = Color.Transparent,
                inputFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            ),
            content = {
                AnimatedVisibility(
                    visible = active,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 280.dp)
                            .background(Color.White)
                            .verticalScroll(rememberScrollState())
                    ) {
                        filteredSuggestions.forEach { suggestion ->
                            ListItem(
                                headlineContent = { Text(suggestion) },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.Place,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                },
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.White,
                                    headlineColor = Color.Black
                                ),
                                modifier = Modifier.clickable {
                                    text = suggestion
                                    onActiveChange(false)
                                    onSearch(suggestion)
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}