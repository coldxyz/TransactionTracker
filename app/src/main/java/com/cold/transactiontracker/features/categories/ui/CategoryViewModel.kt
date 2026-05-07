package com.cold.transactiontracker.features.categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cold.transactiontracker.features.categories.data.Category
import com.cold.transactiontracker.features.categories.data.CategoryRepository
import com.cold.transactiontracker.features.categories.data.CategoryUiState
import com.cold.transactiontracker.features.transactions.data.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(
                name = value,
                isValid = validate(
                    name = value,
                    iconName = it.iconName
                )
            )
        }
    }

    fun onIconChange(value: String) {
        _uiState.update {
            it.copy(
                iconName = value,
                isValid = validate(
                    name = it.name,
                    iconName = value
                )
            )
        }
    }

    fun onTypeChange(type: TransactionType) {
        _uiState.update {
            it.copy(type = type)
        }
    }

    fun onSave() {
        val state = _uiState.value

        val category = Category(
            name = state.name,
            iconName = state.iconName,
            type = state.type
        )

        viewModelScope.launch {
            repository.insertCategory(category)
        }

        _uiState.value = CategoryUiState()
    }

    private fun validate(
        name: String,
        iconName: String
    ): Boolean {
        return name.isNotBlank() &&
                iconName.isNotBlank()
    }

    fun getCategoriesByType(type: TransactionType): Flow<List<Category>> {
        return repository.getCategoriesByType(type)
    }
}