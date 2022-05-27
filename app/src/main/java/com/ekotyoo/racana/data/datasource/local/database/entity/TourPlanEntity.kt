package com.ekotyoo.racana.data.datasource.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.ekotyoo.racana.data.model.DailyItem
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination
import java.time.Instant
import java.time.ZoneId

@Entity(tableName = "tour_plan")
data class TourPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
)

data class TourPlanWithDateAndDestinations(
    @Embedded val tourPlan: TourPlanEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tourPlanId",
        entity = TourPlanDateEntity::class
    )
    val tourPlanDates: List<TourPlanDateWithDestination>,
)

fun TourPlanWithDateAndDestinations.toModel(): TourPlan {
    val dailyList = this.tourPlanDates.mapIndexed { i, date ->
        val destinations = date.destinations.map { destination ->
            TravelDestination(
                name = destination.name,
                imageUrl = destination.imageUrl,
                lat = destination.lat,
                lon = destination.lon,
                location = destination.location,
                isDone = destination.visited,
                expense = destination.expense
            )
        }
        DailyItem(
            number = i + 1,
            date = Instant.ofEpochMilli(date.tourPlanDate.dateMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate(),
            destinationList = destinations
        )
    }
    return TourPlan(
        id = this.tourPlan.id,
        title = this.tourPlan.title,
        description = this.tourPlan.description,
        dailyList = dailyList
    )
}
