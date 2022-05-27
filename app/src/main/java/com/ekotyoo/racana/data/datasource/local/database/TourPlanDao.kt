package com.ekotyoo.racana.data.datasource.local.database

import androidx.room.*
import com.ekotyoo.racana.data.datasource.local.database.entity.DestinationEntity
import com.ekotyoo.racana.data.datasource.local.database.entity.TourPlanDateEntity
import com.ekotyoo.racana.data.datasource.local.database.entity.TourPlanEntity
import com.ekotyoo.racana.data.datasource.local.database.entity.TourPlanWithDateAndDestinations
import com.ekotyoo.racana.data.model.TourPlan
import kotlinx.coroutines.flow.Flow
import java.time.ZoneId

@Dao
interface TourPlanDao {

    @Transaction
    @Query("SELECT * FROM tour_plan")
    fun getAllTourPlan(): Flow<List<TourPlanWithDateAndDestinations>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTourPlanWithDateAndDestinations(
        tourPlan: TourPlan,
        title: String,
        description: String,
    ) {
        val tourPlanId = insertTourPlan(TourPlanEntity(title = title, description = description))

        val tourPlanDateIdList =
            insertTourPlanDate(
                tourPlan.dailyList.map {
                    TourPlanDateEntity(
                        dateMillis = it.date
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli(),
                        tourPlanId = tourPlanId
                    )
                }
            )

        tourPlanDateIdList.forEachIndexed { index, id ->
            val destinations = tourPlan.dailyList[index].destinationList.map {
                DestinationEntity(name = it.name,
                    imageUrl = it.imageUrl,
                    location = it.location,
                    lat = it.lat,
                    lon = it.lon,
                    visited = it.isDone,
                    expense = it.expense,
                    dateId = id)
            }
            insertDestination(destinations)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTourPlan(tourPlanEntity: TourPlanEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTourPlanDate(tourPlanDateEntities: List<TourPlanDateEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDestination(destinationEntities: List<DestinationEntity>)
}