package com.cold.transactiontracker.features.transactions.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cold.transactiontracker.features.transactions.data.model.Transaction
import com.cold.transactiontracker.features.transactions.data.model.TransactionWithCategory

@Composable
fun TransactionsListContent(
    transactions: List<TransactionWithCategory>,
    onDelete: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {

    if (transactions.isEmpty()) {
        EmptyTransactionsState(
            modifier = modifier.fillMaxSize()
        )
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(
            items = transactions,
            key = { it.transaction.id }
        ) { item ->
            TransactionItemCard(
                item = item,
                onDelete = {
                    onDelete(item.transaction)
                }
            )
        }
    }
}