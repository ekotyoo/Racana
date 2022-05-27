package com.ekotyoo.racana.data.datasource.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destination")
data class DestinationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val imageUrl: String,
    val location: String,
    val lat: Double?,
    val lon: Double?,
    val visited: Boolean = false,
    val expense: Long,
    val dateId: Long,
)