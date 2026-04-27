package com.cold.transactiontracker.features.homescreen.ui

import com.cold.transactiontracker.features.homescreen.ui.model.CategoryTotal

data class HomeUiState(
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val balance: Double = 0.0,
    val expensesByCategory: List<CategoryTotal> = emptyList(),
    val incomeByCategory: List<CategoryTotal> = emptyList(),
    val isLoading: Boolean = false
)