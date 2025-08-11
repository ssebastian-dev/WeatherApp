package com.example.weatherdemo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.ui.theme.Gray
import com.example.weatherdemo.ui.theme.Gray2
import com.example.weatherdemo.ui.theme.Gray3
import com.example.weatherdemo.ui.theme.NeutralVariant30

@Composable
fun <T> WeatherForecastCard(
    title: String,
    isHorizontal: Boolean,
    items: List<T>,
    content: @Composable (T) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = NeutralVariant30,
            //contentColor = NeutralVariant30
        )

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                color = Gray3,
                style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness,
                color = Gray3
            )
            if (isHorizontal) {
                LazyRow {
                    items(items) { item ->
                        content(item)
                    }
                }
            } else {
                Column {
                    items.forEach { item ->
                        content(item)
                    }
                }
            }
        }
    }
}
