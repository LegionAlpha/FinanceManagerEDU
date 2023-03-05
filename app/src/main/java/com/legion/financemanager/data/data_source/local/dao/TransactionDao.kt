package com.legion.financemanager.data.data_source.local.dao

import androidx.room.*
import com.legion.financemanager.data.data_source.local.entity.TransactionEntity
import com.legion.financemanager.domain.enums.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long


    @Query("SELECT * FROM TransactionEntity WHERE type = :type")
    fun getTransactions(type: TransactionType): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
}