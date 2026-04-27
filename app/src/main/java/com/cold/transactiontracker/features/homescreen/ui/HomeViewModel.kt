package com.cold.transactiontracker.features.homescreen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cold.transactiontracker.features.transactions.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val WhileSubscribed = SharingStarted.WhileSubscribed(5_000)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        combine(
            repository.getTotalIncome(),
            repository.getTotalExpenses(),
            repository.getExpensesByCategory(),
            repository.getIncomeByCategory()
        ) { totalIncome, totalExpenses, expenses, income ->

            HomeUiState(
                totalIncome = totalIncome,
                totalExpenses = totalExpenses,
                balance = totalIncome - totalExpenses,
                expensesByCategory = expenses,
                incomeByCategory = income,
                isLoading = false
            )
        }.stateIn(
            viewModelScope,
            WhileSubscribed,
            HomeUiState(isLoading = true)
        )
}