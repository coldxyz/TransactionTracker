package com.cold.transactiontracker.core.database

import com.cold.transactiontracker.features.categories.data.Category
import com.cold.transactiontracker.features.categories.data.CategoryRepository
import com.cold.transactiontracker.features.transactions.data.TransactionType
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend fun populate() {
        val existing = categoryRepository.getAllCategories().first()

        if (existing.isEmpty()) {
            categoryRepository.insertDefaults(
                listOf(
                    Category(1, "Food", "food",TransactionType.EXPENSE),
                    Category(2, "Transport", "transport", TransactionType.EXPENSE),
                    Category(3, "Salary", "salary", TransactionType.INCOME),
                    Category(4, "Gift", "gift", TransactionType.INCOME)
                )
            )
        }
    }
}