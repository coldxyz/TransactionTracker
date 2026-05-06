package com.cold.transactiontracker.features.homescreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.core.navigation.ui.BottomNavigationBar
import com.cold.transactiontracker.features.homescreen.ui.components.BalanceCard
import com.cold.transactiontracker.features.homescreen.ui.components.CategoryRow
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.ui.FilteredTransactionsDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionsListDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    navigateToTransactionEntry: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(
                        onClick = navigateToSettings
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToTransactionEntry) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            LoadingState(modifier = Modifier.padding(padding))
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                BalanceCard(
                    balance = uiState.balance,
                    income = uiState.totalIncome,
                    expenses = uiState.totalExpenses,
                    modifier = Modifier.clickable {
                        onNavigate(TransactionsListDestination.route)
                    }
                )
            }

            item {
                SectionHeader(
                    title = "Expenses",
                    enabled = uiState.expensesByCategory.isNotEmpty(),
                    onViewAllClick = {
                        onNavigate(
                            FilteredTransactionsDestination.createRoute(
                                TransactionType.EXPENSE
                            )
                        )
                    }
                )
            }

            items(
                items = uiState.expensesByCategory,
                key = { "expense_${it.categoryName}" }
            ) { item ->
                CategoryRow(
                    name = item.categoryName,
                    amount = item.total,
                    isExpense = true
                )
            }

            item {
                SectionHeader(
                    title = "Income",
                    enabled = uiState.expensesByCategory.isNotEmpty(),
                    onViewAllClick = {
                        onNavigate(
                            FilteredTransactionsDestination.createRoute(
                                TransactionType.INCOME
                            )
                        )
                    }
                )
            }

            items(
                items = uiState.incomeByCategory,
                key = { "income_${it.categoryName}" }
            ) { item ->
                CategoryRow(
                    name = item.categoryName,
                    amount = item.total,
                    isExpense = false
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    enabled: Boolean,
    onViewAllClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)

        Text(
            text = "View All",
            color = if (enabled)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .then(
                    if (enabled) Modifier.clickable { onViewAllClick() }
                    else Modifier
                )
        )
    }
}

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}