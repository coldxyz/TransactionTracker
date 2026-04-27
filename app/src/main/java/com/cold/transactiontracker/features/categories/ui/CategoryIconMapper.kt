package com.cold.transactiontracker.features.categories.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

fun String.toIcon(): ImageVector {
    return when (this) {
        "food" -> Icons.Default.Fastfood
        "transport" -> Icons.Default.DirectionsCar
        "salary" -> Icons.Default.AttachMoney
        "gift" -> Icons.Default.CardGiftcard
        else -> Icons.Default.Category
    }
}