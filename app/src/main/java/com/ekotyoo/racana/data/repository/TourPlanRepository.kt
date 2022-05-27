package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.datasource.local.database.TourPlanDao
import com.ekotyoo.racana.data.datasource.remote.TourPlanApi
import com.ekotyoo.racana.data.model.DailyItem
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@ViewModelScoped
class TourPlanRepository @Inject constructor(
    private val tourPlanApi: TourPlanApi,
    private val tourPlanDao: TourPlanDao,
) {
    fun getSavedTourPlan() = tourPlanDao.getAllTourPlan()

    suspend fun saveTourPlan(tourPlan: TourPlan, title: String, description: String): Result<Unit> {
        return try {
            tourPlanDao.insertTourPlanWithDateAndDestinations(tourPlan, title, description)
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.d(e.message)
            Result.Error(message = e.message.toString(), e)
        }
    }

    suspend fun getTourPlan(
        city: String,
        budget: Long,
        startDateInMillis: Long?,
        endDateInMillis: Long?,
        totalDestination: Int,
        category: Int,
    ): Result<TourPlan> {
        try {
            val response = tourPlanApi.getTourPlan(
                city,
                budget,
                startDateInMillis,
                endDateInMillis,
                totalDestination,
                category
            )

            val body = response.body()
            return if (response.isSuccessful && body != null) {
                val dailyList = mutableListOf<DailyItem>()
                body.dailyList.forEachIndexed { i, item ->
                    val date =
                        Instant.ofEpochMilli(item.date).atZone(ZoneId.systemDefault()).toLocalDate()
                    val destinationList =
                        item.destinations.map {
                            TravelDestination(it.name,
                                it.imageUrl,
                                it.name,
                                it.lat,
                                it.long)
                        }
                    dailyList.add(
                        DailyItem(
                            number = i + 1,
                            date = date,
                            destinationList = destinationList
                        )
                    )
                }
                Result.Success(TourPlan(id = body.id, dailyList = dailyList))
            } else {
                Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = null)
            }
        } catch (e: IOException) {
            Timber.d(e.message)
            return Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        } catch (e: HttpException) {
            Timber.d(e.message)
            return Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        }
    }
}