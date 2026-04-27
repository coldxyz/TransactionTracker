package com.cold.transactiontracker.features.transactions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.ui.components.EmptyTransactionsState
import com.cold.transactiontracker.features.transactions.ui.components.TransactionItemCard

object FilteredTransactionsDestination : NavigationDestination {
    override val route = "filtered_transactions"

    const val TYPE_ARG = "type"

    fun createRoute(type: TransactionType) =
        "$route/${type.name}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredTransactionsScreen(
    type: TransactionType,
    viewModel: TransactionViewModel,
    navigateBack: () -> Unit
) {
    val transactions by viewModel
        .getTransactionsByType(type)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val title = if (type == TransactionType.EXPENSE) {
        "All Expenses"
    } else {
        "All Income"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        if (transactions.isEmpty()) {
            EmptyTransactionsState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(transactions) { item ->
                TransactionItemCard(
                    item = item,
                    onDelete = { viewModel.onDelete(it) }
                )
            }
        }
    }
}