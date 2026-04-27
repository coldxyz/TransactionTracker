package com.cold.transactiontracker.features.categories.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cold.transactiontracker.features.transactions.data.TransactionType


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val iconName: String,
    val type: TransactionType,
)