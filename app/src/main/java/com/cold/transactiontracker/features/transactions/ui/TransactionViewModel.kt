package com.cold.transactiontracker.features.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cold.transactiontracker.features.categories.data.Category
import com.cold.transactiontracker.features.transactions.data.TransactionRepository
import com.cold.transactiontracker.features.transactions.data.TransactionType
import com.cold.transactiontracker.features.transactions.data.TransactionUiState
import com.cold.transactiontracker.features.transactions.data.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val WhileSubscribed = SharingStarted.WhileSubscribed(5_000)

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
) : ViewModel() {

    // ---------------- STATE ----------------

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    // ---------------- DATA STREAMS ----------------

    val transactions = repository.getTransactions()
        .stateIn(viewModelScope, WhileSubscribed, emptyList())

    val transactionsSorted = transactions
        .map { list ->
            list.sortedByDescending { it.transaction.timestamp }
        }
        .stateIn(viewModelScope, WhileSubscribed, emptyList())

    fun getTransactionsByType(type: TransactionType) =
        transactions.map { list ->
            list
                .filter { it.transaction.type == type }
                .sortedByDescending { it.transaction.timestamp }
        }

    val totalIncome = repository.getTotalIncome()
        .stateIn(viewModelScope, WhileSubscribed, 0.0)

    val totalExpenses = repository.getTotalExpenses()
        .stateIn(viewModelScope, WhileSubscribed, 0.0)

    val balance = repository.getBalance()
        .stateIn(viewModelScope, WhileSubscribed, 0.0)

    val expensesByCategory = repository.getExpensesByCategory()
        .stateIn(viewModelScope, WhileSubscribed, emptyList())

    val incomeByCategory = repository.getIncomeByCategory()
        .stateIn(viewModelScope, WhileSubscribed, emptyList())

    // ---------------- EVENTS ----------------

    fun onAmountChange(amount: String) {
        _uiState.update { current ->
            current.copy(
                amount = amount,
                isValid = validate(amount, current.selectedCategory)
            )
        }
    }

    fun onNotesChange(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun onTypeChange(type: TransactionType) {
        _uiState.update { current ->
            if (current.type == type) return@update current

            current.copy(
                type = type,
                selectedCategory = null,
                isValid = validate(current.amount, null)
            )
        }
    }

    fun onDateSelected(date: Long) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { current ->
            current.copy(
                selectedCategory = category,
                isValid = validate(current.amount, category)
            )
        }
    }

    fun onSave() {
        val state = _uiState.value
        val amount = state.amount.toDoubleOrNull() ?: return
        val category = state.selectedCategory ?: return

        viewModelScope.launch {
            repository.insert(
                Transaction(
                    amount = amount,
                    type = state.type,
                    categoryId = category.id,
                    notes = state.notes,
                    timestamp = state.selectedDate
                )
            )

            resetState()
        }
    }

    fun onDelete(transaction: Transaction) {
        viewModelScope.launch {
            repository.delete(transaction)
        }
    }

    fun onCancel() {
        resetState()
    }

    // ---------------- HELPERS ----------------

    private fun resetState() {
        _uiState.value = TransactionUiState()
    }

    private fun validate(amount: String, category: Category?): Boolean {
        val parsedAmount = amount.toDoubleOrNull()
        return parsedAmount != null && parsedAmount > 0 && category != null
    }
}