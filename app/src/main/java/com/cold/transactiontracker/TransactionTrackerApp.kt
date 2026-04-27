package com.cold.transactiontracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cold.transactiontracker.core.navigation.AppNavHost

@Composable
fun TransactionTrackerApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController = navController)
}