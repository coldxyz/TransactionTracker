package com.cold.transactiontracker.features.homescreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.cold.transactiontracker.core.navigation.data.NavigationDestination
import com.cold.transactiontracker.core.navigation.ui.BottomNavigationBar
import com.cold.transactiontracker.features.homescreen.ui.components.HomeContent
import com.cold.transactiontracker.features.homescreen.ui.components.HomeTopBar

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
            HomeTopBar(
                navigateToSettings = navigateToSettings
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTransactionEntry
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Transaction"
                )
            }
        }
    ) { padding ->

        when {
            uiState.isLoading -> {
                LoadingState(
                    modifier = Modifier.padding(padding)
                )
            }

            else -> {
                HomeContent(
                    uiState = uiState,
                    modifier = Modifier.padding(padding),
                    onNavigate = onNavigate
                )
            }
        }
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