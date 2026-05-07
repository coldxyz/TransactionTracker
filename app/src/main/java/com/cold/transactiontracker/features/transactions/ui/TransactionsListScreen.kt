package com.cold.transactiontracker.features.transactions.ui

import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.features.transactions.ui.components.TransactionsListContent

object TransactionsListDestination : NavigationDestination {
    override val route = "transactions_list"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsListScreen(
    viewModel: TransactionViewModel,
    navigateBack: () -> Unit,
    navigateToEditTransaction: (Int) -> Unit
) {

    val transactions by viewModel.transactionsSorted
        .collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Transactions") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null
                        )
                    }
                }
            )
        }
    ) { padding ->

        TransactionsListContent(
            transactions = transactions,

            onDelete = viewModel::onDelete,

            onTransactionClick = { transaction ->

                navigateToEditTransaction(
                    transaction.id
                )
            },

            modifier = Modifier.padding(padding)
        )
    }
}