package com.cold.transactiontracker.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.features.transactions.ui.SaveButton
import com.cold.transactiontracker.features.transactions.ui.TypeSelectorRow

object CategoryEntryDestination : NavigationDestination {
    override val route = "category_entry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryEntryScreen(
    navigateBack: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add category") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CategoryNameInput(
                name = uiState.name,
                onNameChange = viewModel::onNameChange
            )

            IconNameInput(
                iconName = uiState.iconName,
                onIconNameChange = viewModel::onIconChange
            )

            TypeSelectorRow(
                selectedType = uiState.type,
                onTypeSelected = viewModel::onTypeChange
            )

            Spacer(modifier = Modifier.weight(1f))

            SaveButton(
                enabled = uiState.isValid,
                onClick = {
                    viewModel.onSave()
                    navigateBack()
                }
            )
        }
    }
}

@Composable
fun CategoryNameInput(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column {

        Text(
            "Category name",
            style = MaterialTheme.typography.titleMedium
        )

        TextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun IconNameInput(
    iconName: String,
    onIconNameChange: (String) -> Unit
) {
    Column {

        Text(
            "Icon name",
            style = MaterialTheme.typography.titleMedium
        )

        TextField(
            value = iconName,
            onValueChange = onIconNameChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}