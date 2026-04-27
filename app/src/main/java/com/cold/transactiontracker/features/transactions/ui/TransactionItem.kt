package com.cold.transactiontracker.features.transactions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.data.model.TransactionWithCategory

@Composable
fun TransactionItem(
    item: TransactionWithCategory
) {
    val transaction = item.transaction
    val category = item.category

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(category.name)
            Text(
                text = formatDate(transaction.timestamp),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(
            text = if (transaction.type == TransactionType.INCOME) {
                "+%.2f€".format(transaction.amount)
            } else {
                "-%.2f€".format(transaction.amount)
            },
            color = if (transaction.type == TransactionType.INCOME)
                Color(0xFF2E7D32)
            else
                Color(0xFFC62828)
        )
    }
}