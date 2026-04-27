package com.cold.transactiontracker.features.categories.data

import com.cold.transactiontracker.features.transactions.data.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: CategoryDao
) {

    fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories()
    }

    fun getCategoriesByType(type: TransactionType): Flow<List<Category>> {
        return dao.getAllCategories().map { list ->
            list.filter { it.type == type }
        }
    }

    fun getCategoryById(id: Int): Flow<Category?> {
        return dao.getCategoryById(id)
    }

    suspend fun insertDefaults(categories: List<Category>) {
        dao.insertAll(categories)
    }
}