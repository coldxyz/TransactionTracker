package com.cold.transactiontracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cold.transactiontracker.features.categories.data.Category
import com.cold.transactiontracker.features.categories.data.CategoryDao
import com.cold.transactiontracker.features.transactions.data.model.Transaction
import com.cold.transactiontracker.features.transactions.data.TransactionDao

@Database(entities = [Transaction::class,
                     Category::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
}



