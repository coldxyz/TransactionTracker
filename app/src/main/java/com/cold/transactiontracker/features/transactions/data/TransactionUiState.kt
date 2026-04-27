package com.cold.transactiontracker.features.transactions.data

import com.cold.transactiontracker.features.categories.data.Category

data class TransactionUiState(
    val amount: String = "",
    val notes: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val selectedDate: Long = System.currentTimeMillis(),
    val selectedCategory: Category? = null,
    val isValid: Boolean = false
)