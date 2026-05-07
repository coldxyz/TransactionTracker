package com.cold.transactiontracker.features.categories.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cold.transactiontracker.features.transactions.data.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("""
        SELECT * FROM categories
    """)
    fun getAllCategories(): Flow<List<Category>>

    @Query("""
        SELECT * FROM categories
        WHERE id = :id
        LIMIT 1
    """)
    suspend fun getCategoryById(id: Int): Category?

    @Query("""
        SELECT * FROM categories
        WHERE type = :type
    """)
    fun getCategoriesByType(
        type: TransactionType
    ): Flow<List<Category>>

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)
}