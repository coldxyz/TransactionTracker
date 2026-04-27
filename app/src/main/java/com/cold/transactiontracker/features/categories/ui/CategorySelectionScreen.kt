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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionViewModel

object CategorySelectionDestination: NavigationDestination {
    override val route = "category_selection"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectionScreen(
    transactionViewModel: TransactionViewModel,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by transactionViewModel.uiState.collectAsStateWithLifecycle()
    val categories by categoryViewModel.categories.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.type) {
        categoryViewModel.setType(uiState.type)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose a category") },
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
            items(categories, key = { it.id }) { category ->

                CategoryItem(
                    category = category,
                    isSelected = uiState.selectedCategory?.id == category.id,
                    onClick = {
                        transactionViewModel.onCategorySelected(category)
                        navigateBack()
                    }
                )
            }
        }
    }
}