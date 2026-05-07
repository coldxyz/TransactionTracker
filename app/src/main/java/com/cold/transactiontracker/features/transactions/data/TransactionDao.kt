package com.cold.transactiontracker.features.transactions.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cold.transactiontracker.features.transactions.data.model.BalanceResult
import com.cold.transactiontracker.features.transactions.data.model.CategoryTotal
import com.cold.transactiontracker.features.transactions.data.model.Transaction
import com.cold.transactiontracker.features.transactions.data.model.TransactionWithCategory
import kotlinx.coroutines.flow.Flow
import androidx.room.Transaction as RoomTransaction

@Dao
interface TransactionDao {

    // ---------------- BASIC ----------------

    @RoomTransaction
    @Query("""
        SELECT * FROM transactions
    """)
    fun getTransactionsWithCategory(): Flow<List<TransactionWithCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @RoomTransaction
    @Query("""
    SELECT * FROM transactions
    WHERE id = :id
""")
    suspend fun getTransactionWithCategoryById(
        id: Int
    ): TransactionWithCategory?

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
        SELECT 
            c.id AS categoryId,
            c.name AS categoryName,
            SUM(t.amount) AS total
        FROM transactions t
        INNER JOIN categories c 
            ON t.categoryId = c.id
        WHERE t.type = :type
        GROUP BY c.id
        ORDER BY total DESC
    """)
    fun getTotalsByCategory(type: TransactionType): Flow<List<CategoryTotal>>

    @RoomTransaction
        @Query("""
        SELECT * FROM transactions
        WHERE categoryId = :categoryId
        ORDER BY timestamp DESC
    """)
    fun getTransactionsByCategory(
        categoryId: Int
    ): Flow<List<TransactionWithCategory>>
}
