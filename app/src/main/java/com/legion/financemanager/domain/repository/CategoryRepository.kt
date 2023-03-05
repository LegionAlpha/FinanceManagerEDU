package com.legion.financemanager.domain.repository

import com.legion.financemanager.data.data_source.local.entity.CategoryEntity
import com.legion.financemanager.domain.enums.CategoryType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun insertCategory(category: CategoryEntity)


    fun getCategories(type: CategoryType): Flow<List<CategoryEntity>>


    suspend fun updateCategory(category: CategoryEntity)


    suspend fun deleteCategory(category: CategoryEntity)
}