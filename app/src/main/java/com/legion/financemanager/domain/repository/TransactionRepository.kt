package com.legion.financemanager.domain.repository

import com.legion.financemanager.data.data_source.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transaction: TransactionEntity)

    fun getTransactions(): Flow<List<TransactionEntity>>

    suspend fun updateTransaction(transaction: TransactionEntity)

    suspend fun deleteTransaction(transaction: TransactionEntity)
}