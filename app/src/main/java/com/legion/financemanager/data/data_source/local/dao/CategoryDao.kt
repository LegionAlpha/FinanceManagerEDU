package com.legion.financemanager.data.data_source.local.dao

import androidx.room.*
import com.legion.financemanager.data.data_source.local.entity.CategoryEntity
import com.legion.financemanager.domain.enums.CategoryType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("SELECT * FROM CategoryEntity where type = :type")
    fun getCategories(type: CategoryType): Flow<List<CategoryEntity>>

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}