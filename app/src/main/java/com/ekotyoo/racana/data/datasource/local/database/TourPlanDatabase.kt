package com.ekotyoo.racana.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ekotyoo.racana.data.datasource.local.database.entity.*

@Database(
    entities = [
        TourPlanEntity::class,
        TourPlanDateEntity::class,
        DestinationEntity::class
    ],
    version = 1
)
abstract class TourPlanDatabase : RoomDatabase() {
    abstract fun tourPlanDao(): TourPlanDao
}