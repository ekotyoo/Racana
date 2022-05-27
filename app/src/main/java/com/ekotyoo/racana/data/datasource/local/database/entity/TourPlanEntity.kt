package com.ekotyoo.racana.data.datasource.local.database.entity

import androidx.room.*

@Entity(tableName = "tour_plan")
data class TourPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String
)

data class TourPlanWithDateAndDestinations(
    @Embedded val tourPlan: TourPlanEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tourPlanId",
        entity = TourPlanDateEntity::class
    )
    val tourPlanDates: List<TourPlanDateWithDestination>
)
