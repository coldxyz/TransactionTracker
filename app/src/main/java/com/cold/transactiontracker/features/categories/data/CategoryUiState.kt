package com.cold.transactiontracker.features.categories.data

import com.cold.transactiontracker.features.transactions.data.TransactionType

data class CategoryUiState(
    val name: String = "",
    val iconName: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val isValid: Boolean = false
)