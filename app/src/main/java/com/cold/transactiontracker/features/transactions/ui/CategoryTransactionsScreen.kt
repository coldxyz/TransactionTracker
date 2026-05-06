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

object CategoryTransactionsDestination : NavigationDestination {

    override val route = "category_transactions"

    const val CATEGORY_ID_ARG = "categoryId"
    const val CATEGORY_NAME_ARG = "categoryName"

    fun createRoute(
        categoryId: Int,
        categoryName: String
    ): String {

        return "$route/$categoryId/$categoryName"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTransactionsScreen(
    categoryId: Int,
    categoryName: String,
    viewModel: TransactionViewModel,
    navigateBack: () -> Unit
) {

    val transactions by viewModel
        .getTransactionsByCategory(categoryId)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(categoryName)},
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        TransactionsListContent(
            transactions = transactions,
            onDelete = viewModel::onDelete,
            modifier = Modifier.padding(padding)
        )
    }
}