package com.cold.transactiontracker.features.categories.data

import com.cold.transactiontracker.features.transactions.data.TransactionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: CategoryDao
) {

    fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }

    fun getCategoriesByType(
        type: TransactionType
    ): Flow<List<Category>> {
        return dao.getCategoriesByType(type)
    }

    suspend fun getCategoryById(id: Int): Category? {
        return dao.getCategoryById(id)
    }

    suspend fun saveCategory(category: Category) {

        if (category.id == 0) {
            dao.insertCategory(category)
        } else {
            dao.updateCategory(category)
        }
    }

    suspend fun insertDefaults(categories: List<Category>) {
        dao.insertAll(categories)
    }

    suspend fun deleteCategory(category: Category) {
        dao.deleteCategory(category)
    }
}