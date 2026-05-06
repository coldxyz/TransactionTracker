package com.cold.transactiontracker.features.homescreen.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CategoryRow(
    name: String,
    amount: Double,
    isExpense: Boolean,
    onClick: () -> Unit
) {
    val color = if (isExpense) Color(0xFFC62828) else Color(0xFF2E7D32)
    val prefix = if (isExpense) "- €" else "+ €"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)

        Text(
            text = "$prefix%.2f".format(amount),
            color = color
        )
    }
}