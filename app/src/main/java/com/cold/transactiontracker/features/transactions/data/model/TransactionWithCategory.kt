package com.cold.transactiontracker.features.transactions.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.cold.transactiontracker.features.categories.data.Category

data class TransactionWithCategory(
    @Embedded val transaction: Transaction,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category
)