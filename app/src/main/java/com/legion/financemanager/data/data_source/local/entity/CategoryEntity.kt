package com.legion.financemanager.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
//    val type: CategoryType,
    val imageUrl: String,
)