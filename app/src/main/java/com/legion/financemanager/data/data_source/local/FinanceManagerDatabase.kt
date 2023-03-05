package com.legion.financemanager.data.data_source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.legion.financemanager.data.data_source.local.converters.TimeConverters
import com.legion.financemanager.data.data_source.local.dao.TransactionDao
import com.legion.financemanager.data.data_source.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
    ],
    version = 1
)
@TypeConverters(TimeConverters::class)
abstract class FinanceManagerDatabase: RoomDatabase() {

    abstract val transactionDao: TransactionDao

    companion object {
        const val DATABASE_NAME = "financeManager_db"

        @Volatile private var instance: FinanceManagerDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            FinanceManagerDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}