package com.example.weatherdemo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.domain.models.City
import com.example.weatherdemo.ui.theme.Gray3
import com.example.weatherdemo.ui.theme.NeutralVariant30

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    citySearchResults: List<City>,
    isSearching: Boolean,
    citySearchError: String?,
    onCitySelected: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(modifier) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            label = { Text("Search for a city") },
            textStyle = TextStyle(color = Gray3, fontWeight = FontWeight.Bold),
            singleLine = true,
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { onSearchTextChange("") }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear search")
                    }
                } else {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                // Optionally trigger search explicitly here
            })
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (isFocused) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                when {
                    isSearching -> {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Searching...", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    citySearchError != null -> {
                        Text(
                            citySearchError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    citySearchResults.isEmpty() -> {
                        Text(
                            "No results found",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.background(NeutralVariant30)) {
                            items(citySearchResults, key = { it.name + it.lat + it.lon }) { city ->
                                DropdownMenuItem(
                                    onClick = {
                                        onCitySelected(city)
                                        focusManager.clearFocus()
                                    },
                                    text = {
                                        Text(
                                            city.displayName(),
                                            color = Gray3,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}