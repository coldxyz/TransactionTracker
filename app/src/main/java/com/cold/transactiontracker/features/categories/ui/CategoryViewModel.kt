package com.cold.transactiontracker.features.categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cold.transactiontracker.features.categories.data.Category
import com.cold.transactiontracker.features.categories.data.CategoryRepository
import com.cold.transactiontracker.features.transactions.data.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val WhileSubscribed = SharingStarted.WhileSubscribed(5_000)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _selectedType = MutableStateFlow(TransactionType.EXPENSE)

    fun setType(type: TransactionType) {
        _selectedType.value = type
    }

    val categories: StateFlow<List<Category>> =
        _selectedType
            .flatMapLatest { type ->
                repository.getCategoriesByType(type)
            }
            .stateIn(viewModelScope, WhileSubscribed, emptyList())
}