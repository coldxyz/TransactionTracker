package com.cold.transactiontracker.features.homescreen.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SectionHeader(
    title: String,
    enabled: Boolean,
    onViewAllClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)

        Text(
            text = "View All",
            color = if (enabled)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .then(
                    if (enabled) Modifier.clickable { onViewAllClick() }
                    else Modifier
                )
        )
    }
}