package com.cold.transactiontracker.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
) {
    val categories by viewModel.allCategories.collectAsStateWithLifecycle()

    val groupedCategories = categories.groupBy { it.type }

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
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            groupedCategories[TransactionType.EXPENSE]?.let { expenseCategories ->

                item {
                    Text(
                        text = "Expenses",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(
                    expenseCategories,
                    key = { it.id }
                ) { category ->

                    CategoryItem(
                        category = category,
                        isSelected = false,
                        onClick = { }
                    )
                }
            }

            groupedCategories[TransactionType.INCOME]?.let { incomeCategories ->

                item {
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )
                }

                items(
                    incomeCategories,
                    key = { it.id }
                ) { category ->

                    CategoryItem(
                        category = category,
                        isSelected = false,
                        onClick = { }
                    )
                }
            }
        }
    }
}