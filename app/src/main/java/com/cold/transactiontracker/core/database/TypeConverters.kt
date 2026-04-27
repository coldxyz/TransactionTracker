package com.cold.transactiontracker.core.database

import androidx.room.TypeConverter
import com.cold.transactiontracker.features.transactions.data.TransactionType

class Converters {

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}