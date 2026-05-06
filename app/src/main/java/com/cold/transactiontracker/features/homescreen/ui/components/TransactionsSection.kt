package com.cold.transactiontracker.features.homescreen.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.cold.transactiontracker.features.homescreen.ui.model.CategoryTotal

@Composable
fun TransactionsSection(
    title: String,
    items: List<CategoryTotal>,
    isExpense: Boolean,
    onViewAllClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SectionHeader(
            title = title,
            enabled = items.isNotEmpty(),
            onViewAllClick = onViewAllClick
        )

        items.forEach { item ->

            CategoryRow(
                name = item.categoryName,
                amount = item.total,
                isExpense = isExpense
            )
        }
    }
}