package com.cold.transactiontracker.features.transactions.data

import com.cold.transactiontracker.features.transactions.data.model.Transaction
import com.cold.transactiontracker.features.transactions.data.model.TransactionWithCategory
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val dao: TransactionDao
) {

    fun getTransactions() = dao.getTransactionsWithCategory()

    fun getTotalIncome() =
        dao.getTotalByType(TransactionType.INCOME)

    fun getTotalExpenses() =
        dao.getTotalByType(TransactionType.EXPENSE)

    fun getBalance() =
        dao.getBalance().map { it.totalIncome - it.totalExpenses }

    fun getExpensesByCategory() =
        dao.getTotalsByCategory(TransactionType.EXPENSE)

    fun getIncomeByCategory() =
        dao.getTotalsByCategory(TransactionType.INCOME)

    fun getTransactionsByCategory(categoryId: Int) =
        dao.getTransactionsByCategory(categoryId)

    suspend fun getTransactionWithCategoryById(
        id: Int
    ): TransactionWithCategory? {
        return dao.getTransactionWithCategoryById(id)
    }

    suspend fun upsertTransaction(transaction: Transaction) =
        dao.upsertTransaction(transaction)

    suspend fun deleteTransaction(transaction: Transaction) =
        dao.deleteTransaction(transaction)
}