package com.ekotyoo.racana.data.datasource.local.database.entity

import androidx.room.*

@Entity(tableName = "tour_plan_date")
data class TourPlanDateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateMillis: Long,
    val tourPlanId: Long
)

data class  TourPlanDateWithDestination(
    @Embedded val tourPlanDate: TourPlanDateEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "dateId"
    )
    val destinations: List<DestinationEntity>
)
