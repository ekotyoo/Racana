package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.datasource.remote.TourPlanApi
import com.ekotyoo.racana.ui.main.dashboard.model.TravelDestination
import com.ekotyoo.racana.ui.main.tourplanresult.model.DailyItem
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlan
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@ViewModelScoped
class TourPlanRepository @Inject constructor(private val tourPlanApi: TourPlanApi) {

    suspend fun getTourPlan(
        city: String,
        budget: Long,
        startDateInMillis: Long?,
        endDateInMillis: Long?,
        totalDestination: Int,
        category: Int
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
                        item.destinations.map { TravelDestination(it.name, it.imageUrl, it.name, it.lat, it.long) }
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