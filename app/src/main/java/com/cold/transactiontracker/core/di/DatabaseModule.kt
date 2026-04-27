package com.cold.transactiontracker.core.di

import android.content.Context
import androidx.room.Room
import com.cold.transactiontracker.core.database.AppDatabase
import com.cold.transactiontracker.features.categories.data.CategoryDao
import com.cold.transactiontracker.features.transactions.data.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "transaction-db"
        ).build()
    }

    @Provides
    fun provideTransactionDao(
        db: AppDatabase
    ): TransactionDao = db.transactionDao()

    @Provides
    fun provideCategoryDao(
        db: AppDatabase
    ): CategoryDao = db.categoryDao()
}