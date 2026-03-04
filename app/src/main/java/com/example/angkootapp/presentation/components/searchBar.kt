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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar(
    modifier: Modifier = Modifier,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onNotificationClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    val suggestions = listOf(
        "Terminal Arjosari",
        "Terminal Landungsari",
        "Terminal Hamid Rusdi"
    )

    val filteredSuggestions = suggestions.filter {
        it.contains(text, ignoreCase = true)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
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
                placeholder = { Text("Cari terminal / Angkot...", fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                trailingIcon = {
                    if (active && text.isNotEmpty()) {
                        IconButton(onClick = { text = "" }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White,
                    dividerColor = Color.Transparent
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
                                    headlineContent = { Text(suggestion, color = Color.Black) },
                                    leadingContent = {
                                        Icon(
                                            Icons.Default.Place,
                                            contentDescription = null,
                                            tint = Color.Gray
                                        )
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .clickable {
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

        if (!active) {
            Spacer(modifier = Modifier.width(12.dp))
            Surface(
                onClick = onNotificationClick,
                modifier = Modifier
                    .size(48.dp)
                    .shadow(elevation = 15.dp, shape = CircleShape),
                shape = CircleShape,
                color = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}