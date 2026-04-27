package com.cold.transactiontracker.features.transactions.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cold.transactiontracker.features.categories.data.Category
import com.cold.transactiontracker.features.transactions.data.TransactionType

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(value = ["categoryId"]),
        Index(value = ["timestamp"]),
        Index(value = ["type"])
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val type: TransactionType,
    val categoryId: Int,
    val notes: String?,
    val timestamp: Long = System.currentTimeMillis()
)