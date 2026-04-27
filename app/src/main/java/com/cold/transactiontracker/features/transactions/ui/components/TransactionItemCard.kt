package com.cold.transactiontracker.features.transactions.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.data.model.Transaction
import com.cold.transactiontracker.features.transactions.data.model.TransactionWithCategory
import com.cold.transactiontracker.features.transactions.ui.formatDate

@Composable
fun TransactionItemCard(
    item: TransactionWithCategory,
    onDelete: (Transaction) -> Unit
) {
    val isExpense = item.transaction.type == TransactionType.EXPENSE
    val color = if (isExpense) Color(0xFFC62828) else Color(0xFF2E7D32)
    val prefix = if (isExpense) "- €" else "+ €"

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.category.name)

                Text(
                    "$prefix%.2f".format(item.transaction.amount),
                    color = color
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(formatDate(item.transaction.timestamp))

            item.transaction.notes?.takeIf { it.isNotBlank() }?.let {
                Spacer(Modifier.height(4.dp))
                Text(it)
            }

            Spacer(Modifier.height(8.dp))
            /*
            Text(
                text = "Delete",
                color = Color.Red,
                modifier = Modifier.clickable {
                    onDelete(item.transaction)
                }
            )
            */
        }
    }
}