package com.cold.transactiontracker.features.transactions.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.cold.transactiontracker.features.homescreen.ui.model.CategoryTotal
import com.cold.transactiontracker.features.transactions.data.model.BalanceResult
import com.cold.transactiontracker.features.transactions.data.model.TransactionWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    // ---------------- BASIC ----------------

    @Transaction
    @Query("""
        SELECT * FROM transactions
    """)
    fun getTransactionsWithCategory(): Flow<List<TransactionWithCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTransaction(transaction: com.cold.transactiontracker.features.transactions.data.model.Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: com.cold.transactiontracker.features.transactions.data.model.Transaction)

    // ---------------- TOTALS ----------------

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE type = :type
    """)
    fun getTotalByType(type: TransactionType): Flow<Double>

    // ---------------- BALANCE ----------------

    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amount END), 0) as totalIncome,
            COALESCE(SUM(CASE WHEN type = 'EXPENSE' THEN amount END), 0) as totalExpenses
        FROM transactions
    """)
    fun getBalance(): Flow<BalanceResult>

    // ---------------- CATEGORY TOTALS ----------------

    @Query("""
        SELECT c.name as categoryName, SUM(t.amount) as total
        FROM transactions t
        INNER JOIN categories c ON t.categoryId = c.id
        WHERE t.type = :type
        GROUP BY c.id
        ORDER BY total DESC
    """)
    fun getTotalsByCategory(type: TransactionType): Flow<List<CategoryTotal>>
}
