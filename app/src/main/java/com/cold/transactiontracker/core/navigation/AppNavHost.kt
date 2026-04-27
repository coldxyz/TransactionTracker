package com.cold.transactiontracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.cold.transactiontracker.features.categories.ui.CategorySelectionDestination
import com.cold.transactiontracker.features.categories.ui.CategorySelectionScreen
import com.cold.transactiontracker.features.categories.ui.CategoryViewModel
import com.cold.transactiontracker.features.homescreen.ui.HomeDestination
import com.cold.transactiontracker.features.homescreen.ui.HomeScreen
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.ui.FilteredTransactionsDestination
import com.cold.transactiontracker.features.transactions.ui.FilteredTransactionsScreen
import com.cold.transactiontracker.features.transactions.ui.TransactionEntryDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionEntryScreen
import com.cold.transactiontracker.features.transactions.ui.TransactionViewModel
import com.cold.transactiontracker.features.transactions.ui.TransactionsListDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionsListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(HomeDestination.route) {
            HomeScreen(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToTransactionEntry = {
                    navController.navigate(TransactionEntryDestination.route)
                }
            )
        }

        composable(TransactionsListDestination.route) {

            val viewModel: TransactionViewModel = hiltViewModel()

            TransactionsListScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${FilteredTransactionsDestination.route}/{${FilteredTransactionsDestination.TYPE_ARG}}"
        ) { backStackEntry ->

            val typeString = backStackEntry.arguments?.getString(
                FilteredTransactionsDestination.TYPE_ARG
            ) ?: TransactionType.EXPENSE.name

            val type = TransactionType.valueOf(typeString)

            val viewModel: TransactionViewModel = hiltViewModel()

            FilteredTransactionsScreen(
                type = type,
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() }
            )
        }

        navigation(
            startDestination = TransactionEntryDestination.route,
            route = "transaction_flow"
        ) {
            composable(TransactionEntryDestination.route) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("transaction_flow")
                }

                val viewModel: TransactionViewModel = hiltViewModel(parentEntry)

                TransactionEntryScreen(
                    viewModel = viewModel,
                    navigateBack = { navController.popBackStack() },
                    navigateToCategorySelection = {
                        navController.navigate(CategorySelectionDestination.route)
                    }
                )
            }

            composable(CategorySelectionDestination.route) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("transaction_flow")
                }

                val transactionViewModel: TransactionViewModel = hiltViewModel(parentEntry)
                val categoryViewModel: CategoryViewModel = hiltViewModel()

                CategorySelectionScreen(
                    transactionViewModel = transactionViewModel,
                    categoryViewModel = categoryViewModel,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}