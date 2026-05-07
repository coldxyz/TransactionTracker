package com.cold.transactiontracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.cold.transactiontracker.features.categories.ui.AddCategoryDestination
import com.cold.transactiontracker.features.categories.ui.CategoriesDestination
import com.cold.transactiontracker.features.categories.ui.CategoriesListScreen
import com.cold.transactiontracker.features.categories.ui.CategoryEntryScreen
import com.cold.transactiontracker.features.categories.ui.CategoryFlowDestination
import com.cold.transactiontracker.features.categories.ui.CategorySelectionDestination
import com.cold.transactiontracker.features.categories.ui.CategorySelectionScreen
import com.cold.transactiontracker.features.categories.ui.CategoryViewModel
import com.cold.transactiontracker.features.categories.ui.EditCategoryDestination
import com.cold.transactiontracker.features.homescreen.ui.HomeDestination
import com.cold.transactiontracker.features.homescreen.ui.HomeScreen
import com.cold.transactiontracker.features.settings.ui.SettingsDestination
import com.cold.transactiontracker.features.settings.ui.SettingsScreen
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.ui.AddTransactionDestination
import com.cold.transactiontracker.features.transactions.ui.CategoryTransactionsDestination
import com.cold.transactiontracker.features.transactions.ui.CategoryTransactionsScreen
import com.cold.transactiontracker.features.transactions.ui.EditTransactionDestination
import com.cold.transactiontracker.features.transactions.ui.FilteredTransactionsDestination
import com.cold.transactiontracker.features.transactions.ui.FilteredTransactionsScreen
import com.cold.transactiontracker.features.transactions.ui.TransactionEntryScreen
import com.cold.transactiontracker.features.transactions.ui.TransactionFlowDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionViewModel
import com.cold.transactiontracker.features.transactions.ui.TransactionsListDestination
import com.cold.transactiontracker.features.transactions.ui.TransactionsListScreen

private fun NavHostController.navigateBackOrHome() {

    val popped = popBackStack()

    if (!popped) {

        navigate(HomeDestination.route) {

            popUpTo(graph.startDestinationId) {
                inclusive = true
            }

            launchSingleTop = true
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {

        // ---------------- HOME ----------------

        composable(HomeDestination.route) {

            HomeScreen(
                currentRoute = currentRoute,

                onNavigate = { route ->

                    navController.navigate(route) {

                        popUpTo(
                            navController.graph.startDestinationId
                        ) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                navigateToTransactionEntry = {

                    navController.navigate(
                        TransactionFlowDestination.route
                    )
                },

                navigateToSettings = {

                    navController.navigate(
                        SettingsDestination.route
                    )
                }
            )
        }

        // ---------------- SETTINGS ----------------

        composable(SettingsDestination.route) {

            SettingsScreen(

                navigateBack = {
                    navController.navigateBackOrHome()
                },

                navigateToCategories = {

                    navController.navigate(
                        CategoriesDestination.route
                    )
                },

                navigateToRecurring = {
                    // TODO
                }
            )
        }

        // ---------------- CATEGORIES LIST ----------------

        composable(CategoriesDestination.route) {

            CategoriesListScreen(

                navigateBack = {
                    navController.navigateBackOrHome()
                },

                navigateToAddCategory = {

                    navController.navigate(
                        CategoryFlowDestination.route
                    )
                },

                navigateToEditCategory = { categoryId ->

                    navController.navigate(
                        EditCategoryDestination.createRoute(
                            categoryId
                        )
                    )
                }
            )
        }

        // ---------------- CATEGORY FLOW ----------------

        navigation(
            startDestination = "category_root",
            route = CategoryFlowDestination.route
        ) {

            composable("category_root") {

                LaunchedEffect(Unit) {

                    navController.navigate(
                        AddCategoryDestination.route
                    ) {

                        popUpTo("category_root") {
                            inclusive = true
                        }
                    }
                }
            }

            composable(
                route = AddCategoryDestination.route
            ) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {

                    navController.getBackStackEntry(
                        CategoryFlowDestination.route
                    )
                }

                val categoryViewModel: CategoryViewModel =
                    hiltViewModel(parentEntry)

                CategoryEntryScreen(
                    viewModel = categoryViewModel,

                    navigateBack = {
                        navController.navigateBackOrHome()
                    }
                )
            }

            composable(
                route = EditCategoryDestination.route,

                arguments = listOf(
                    navArgument(
                        EditCategoryDestination.categoryIdArg
                    ) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {

                    navController.getBackStackEntry(
                        CategoryFlowDestination.route
                    )
                }

                val categoryViewModel: CategoryViewModel =
                    hiltViewModel(parentEntry)

                val categoryId =
                    backStackEntry.arguments!!
                        .getInt(
                            EditCategoryDestination.categoryIdArg
                        )

                CategoryEntryScreen(
                    viewModel = categoryViewModel,

                    categoryId = categoryId,

                    navigateBack = {
                        navController.navigateBackOrHome()
                    }
                )
            }
        }

        // ---------------- TRANSACTIONS LIST ----------------

        composable(TransactionsListDestination.route) {

            val transactionViewModel: TransactionViewModel =
                hiltViewModel()

            TransactionsListScreen(
                viewModel = transactionViewModel,

                navigateBack = {
                    navController.navigateBackOrHome()
                },

                navigateToEditTransaction = { transactionId ->

                    navController.navigate(
                        EditTransactionDestination.createRoute(
                            transactionId
                        )
                    )
                }
            )
        }

        // ---------------- FILTERED TRANSACTIONS ----------------

        composable(
            route =
                "${FilteredTransactionsDestination.route}/" +
                        "{${FilteredTransactionsDestination.TYPE_ARG}}"
        ) { backStackEntry ->

            val typeString =
                backStackEntry.arguments?.getString(
                    FilteredTransactionsDestination.TYPE_ARG
                ) ?: TransactionType.EXPENSE.name

            val type =
                TransactionType.valueOf(typeString)

            val viewModel: TransactionViewModel =
                hiltViewModel()

            FilteredTransactionsScreen(
                type = type,

                viewModel = viewModel,

                navigateBack = {
                    navController.navigateBackOrHome()
                },

                navigateToEditTransaction = { transactionId ->

                    navController.navigate(
                        EditTransactionDestination.createRoute(
                            transactionId
                        )
                    )
                }
            )
        }

        // ---------------- CATEGORY TRANSACTIONS ----------------

        composable(
            route =
                "${CategoryTransactionsDestination.route}/" +
                        "{${CategoryTransactionsDestination.CATEGORY_ID_ARG}}/" +
                        "{${CategoryTransactionsDestination.CATEGORY_NAME_ARG}}"
        ) { backStackEntry ->

            val categoryId =
                backStackEntry.arguments
                    ?.getString(
                        CategoryTransactionsDestination.CATEGORY_ID_ARG
                    )
                    ?.toIntOrNull()
                    ?: return@composable

            val categoryName =
                backStackEntry.arguments
                    ?.getString(
                        CategoryTransactionsDestination.CATEGORY_NAME_ARG
                    )
                    ?: "Category"

            val viewModel: TransactionViewModel =
                hiltViewModel()

            CategoryTransactionsScreen(
                categoryId = categoryId,

                categoryName = categoryName,

                viewModel = viewModel,

                navigateToEditTransaction = { transactionId ->

                    navController.navigate(
                        EditTransactionDestination.createRoute(
                            transactionId
                        )
                    )
                },

                navigateBack = {
                    navController.navigateBackOrHome()
                }
            )
        }

        // ---------------- TRANSACTION FLOW ----------------

        navigation(
            startDestination = "transaction_root",
            route = TransactionFlowDestination.route
        ) {

            composable("transaction_root") {

                LaunchedEffect(Unit) {

                    navController.navigate(
                        AddTransactionDestination.route
                    ) {

                        popUpTo("transaction_root") {
                            inclusive = true
                        }
                    }
                }
            }

            composable(
                route = AddTransactionDestination.route
            ) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {

                    navController.getBackStackEntry(
                        TransactionFlowDestination.route
                    )
                }

                val transactionViewModel: TransactionViewModel =
                    hiltViewModel(parentEntry)

                TransactionEntryScreen(
                    viewModel = transactionViewModel,

                    navigateBack = {
                        navController.navigateBackOrHome()
                    },

                    navigateToCategorySelection = {

                        navController.navigate(
                            CategorySelectionDestination.route
                        )
                    }
                )
            }

            composable(
                route = EditTransactionDestination.route,

                arguments = listOf(
                    navArgument(
                        EditTransactionDestination.transactionIdArg
                    ) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {

                    navController.getBackStackEntry(
                        TransactionFlowDestination.route
                    )
                }

                val transactionViewModel: TransactionViewModel =
                    hiltViewModel(parentEntry)

                val transactionId =
                    backStackEntry.arguments!!
                        .getInt(
                            EditTransactionDestination.transactionIdArg
                        )

                TransactionEntryScreen(
                    viewModel = transactionViewModel,

                    transactionId = transactionId,

                    navigateBack = {
                        navController.navigateBackOrHome()
                    },

                    navigateToCategorySelection = {

                        navController.navigate(
                            CategorySelectionDestination.route
                        )
                    }
                )
            }

            composable(
                CategorySelectionDestination.route
            ) { backStackEntry ->

                val parentEntry = remember(backStackEntry) {

                    navController.getBackStackEntry(
                        TransactionFlowDestination.route
                    )
                }

                val transactionViewModel: TransactionViewModel =
                    hiltViewModel(parentEntry)

                val categoryViewModel: CategoryViewModel =
                    hiltViewModel()

                CategorySelectionScreen(
                    transactionViewModel = transactionViewModel,

                    categoryViewModel = categoryViewModel,

                    navigateBack = {
                        navController.navigateBackOrHome()
                    }
                )
            }
        }
    }
}
