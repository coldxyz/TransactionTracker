package com.cold.transactiontracker.features.homescreen.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cold.transactiontracker.features.homescreen.ui.HomeUiState
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.ui.CategoryTransactionsDestination
import com.cold.transactiontracker.features.transactions.ui.FilteredTransactionsDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionsListDestination

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            BalanceCard(
                balance = uiState.balance,
                income = uiState.totalIncome,
                expenses = uiState.totalExpenses,
                modifier = Modifier.clickable {
                    onNavigate(
                        TransactionsListDestination.route
                    )
                }
            )
        }

        item {
            TransactionsSection(
                title = "Expenses",
                items = uiState.expensesByCategory,
                isExpense = true,
                onViewAllClick = {
                    onNavigate(
                        FilteredTransactionsDestination
                            .createRoute(TransactionType.EXPENSE)
                    )
                },
                onCategoryClick = { categoryId, categoryName ->
                    onNavigate(
                        CategoryTransactionsDestination.createRoute(
                            categoryId = categoryId,
                            categoryName = categoryName
                        )
                    )
                }
            )
        }

        item {
            TransactionsSection(
                title = "Income",
                items = uiState.incomeByCategory,
                isExpense = false,
                onViewAllClick = {
                    onNavigate(
                        FilteredTransactionsDestination
                            .createRoute(TransactionType.INCOME)
                    )
                },
                onCategoryClick = { categoryId, categoryName ->
                    onNavigate(
                        CategoryTransactionsDestination.createRoute(
                            categoryId = categoryId,
                            categoryName = categoryName
                        )
                    )
                }
            )
        }
    }
}