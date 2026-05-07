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

    private var hasLoadedCategory = false

    private val _uiState =
        MutableStateFlow(CategoryUiState())

    val uiState: StateFlow<CategoryUiState> =
        _uiState.asStateFlow()

    fun getCategoriesByType(
        type: TransactionType
    ): Flow<List<Category>> {

        return repository.getCategoriesByType(type)
    }

    fun loadCategory(categoryId: Int) {

        if (hasLoadedCategory) return

        hasLoadedCategory = true

        viewModelScope.launch {

            val category =
                repository.getCategoryById(categoryId)
                    ?: return@launch

            _uiState.value = CategoryUiState(
                editingCategoryId = category.id,
                name = category.name,
                iconName = category.iconName,
                type = category.type,
                isValid = true
            )
        }
    }

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
            id = state.editingCategoryId ?: 0,
            name = state.name,
            iconName = state.iconName,
            type = state.type
        )

        viewModelScope.launch {

            repository.saveCategory(category)

            resetState()
        }
    }

    fun deleteCurrentCategory() {

        val state = _uiState.value

        val category = Category(
            id = state.editingCategoryId ?: return,
            name = state.name,
            iconName = state.iconName,
            type = state.type
        )

        viewModelScope.launch {

            repository.deleteCategory(category)

            resetState()
        }
    }

    fun onCancel() {
        resetState()
    }

    private fun resetState() {

        hasLoadedCategory = false

        _uiState.value = CategoryUiState()
    }

    private fun validate(
        name: String,
        iconName: String
    ): Boolean {

        return name.isNotBlank() &&
                iconName.isNotBlank()
    }
}