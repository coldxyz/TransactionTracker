package com.cold.transactiontracker.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.features.transactions.data.TransactionType

object CategoriesDestination : NavigationDestination {
    override val route = "categories"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesListScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToEditCategory: (Int) -> Unit
) {

    val expenseCategories by viewModel
        .getCategoriesByType(TransactionType.EXPENSE)
        .collectAsStateWithLifecycle(emptyList())

    val incomeCategories by viewModel
        .getCategoriesByType(TransactionType.INCOME)
        .collectAsStateWithLifecycle(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddCategory
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Expenses",
                )
            }

            items(
                expenseCategories,
                key = { it.id }
            ) { category ->

                CategoryItem(
                    category = category,
                    isSelected = false,
                    onClick = {
                        navigateToEditCategory(
                            category.id
                        )
                    }
                )
            }

            item {
                Text(
                    text = "Income",
                )
            }

            items(
                incomeCategories,
                key = { it.id }
            ) { category ->

                CategoryItem(
                    category = category,
                    isSelected = false,
                    onClick = {
                        navigateToEditCategory(
                            category.id
                        )
                    }
                )
            }
        }
    }
}