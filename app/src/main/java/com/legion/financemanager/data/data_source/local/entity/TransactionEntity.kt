package com.legion.financemanager.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.legion.financemanager.domain.enums.TransactionType
import java.util.*

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var categoryId: Int,
    var name: String,
    var amount: Double,
    var comment: String?,
    var type: TransactionType,
    var date: Date = Date(),
)