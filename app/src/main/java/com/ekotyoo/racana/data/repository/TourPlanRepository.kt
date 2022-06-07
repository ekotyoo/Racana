package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.datasource.local.UserPreferencesDataStore
import com.ekotyoo.racana.data.datasource.remote.api.TourPlanApi
import com.ekotyoo.racana.data.datasource.remote.request.TourPlanDateRequest
import com.ekotyoo.racana.data.datasource.remote.request.TourPlanRequest
import com.ekotyoo.racana.data.model.DailyItem
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@ViewModelScoped
class TourPlanRepository @Inject constructor(
    private val tourPlanApi: TourPlanApi,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) {
    suspend fun getSavedTourPlan(): Result<List<TourPlan>> {
        try {
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.getAllTourPlan(token ?: "")
            val data = response.body()?.data

            if (response.isSuccessful && data != null) {
                val tourPlans = data.map {
                    TourPlan(
                        id = it.id.toLong(),
                        title = it.title,
                        description = it.description,
                        dailyList = it.tourPlanDates.mapIndexed { i, date ->
                            DailyItem(
                                id = date.id,
                                number = i + 1,
                                date = Instant.ofEpochMilli(date.dateMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate(),
                                destinationList = date.destinations.map { destination ->
                                    TravelDestination(
                                        id = destination.id,
                                        name = destination.name,
                                        description = destination.description,
                                        city = destination.city,
                                        address = destination.address,
                                        rating = destination.rating,
                                        imageUrl = destination.imageUrl,
                                        weekdayPrice = destination.weekdayPrice,
                                        weekendHolidayPrice = destination.weekendHolidayPrice,
                                        lat = destination.lat ?: .0,
                                        lon = destination.lon ?: .0,
                                        categoryId = destination.categoryId ?: 0,
                                    )
                                }
                            )
                        }.sortedBy { item ->
                            item.date
                        }
                    )
                }

                return Result.Success(tourPlans)
            } else {
                return Result.Error("Gagal mengambil data.", null)
            }
        } catch (e: IOException) {
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun getSavedTourPlanById(id: Int): Result<TourPlan> {
        try {
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.getTourPlanById(token ?: "", id)
            val data = response.body()?.data

            if (response.isSuccessful && data != null) {
                val tourPlans = TourPlan(
                    id = data.id.toLong(),
                    title = data.title,
                    description = data.description,
                    isActive = data.isActive,
                    dailyList = data.tourPlanDates.mapIndexed { i, date ->
                        DailyItem(
                            id = date.id,
                            number = i + 1,
                            date = Instant.ofEpochMilli(date.dateMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate(),
                            destinationList = date.destinations.map { destination ->
                                TravelDestination(
                                    id = destination.id,
                                    name = destination.name,
                                    description = destination.description,
                                    city = destination.city,
                                    address = destination.address,
                                    rating = destination.rating,
                                    imageUrl = destination.imageUrl,
                                    weekdayPrice = destination.weekdayPrice,
                                    weekendHolidayPrice = destination.weekendHolidayPrice,
                                    lat = destination.lat ?: .0,
                                    lon = destination.lon ?: .0,
                                    categoryId = destination.categoryId ?: 0,
                                    isDone = destination.relation.isDone
                                )
                            }
                        )
                    }.sortedBy { item ->
                        item.date
                    }
                )
                return Result.Success(tourPlans)
            } else {
                return Result.Error("Gagal mengambil data.", null)
            }
        } catch (e: IOException) {
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun saveTourPlan(tourPlan: TourPlan, title: String, description: String): Result<Unit> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val requestBody = TourPlanRequest(
                title = title,
                description = description,
                tourPlanDates = tourPlan.dailyList.map {
                    TourPlanDateRequest(
                        dateMillis = it.date.atStartOfDay(ZoneId.systemDefault()).toInstant()
                            .toEpochMilli(),
                        destinations = it.destinationList.map { destination -> destination.id }
                    )
                }
            )
            val response = tourPlanApi.saveTourPlan(token ?: "", requestBody)
            if (response.isSuccessful && response.body()?.status == "Success") {
                Result.Success(Unit)
            } else {
                Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
            }
        } catch (e: IOException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        } catch (e: HttpException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        }
    }

    suspend fun deleteTourPlan(id: Int): Result<Unit> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.deleteTourPlanById(token ?: "", id)
            val body = response.body()

            if (response.isSuccessful && body?.status == "Success") {
                Result.Success(Unit)
            } else {
                Result.Error(message = "Gagal menghapus data.", throwable = null)
            }
        } catch (e: IOException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        } catch (e: HttpException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        }
    }

    suspend fun updateTourPlan(id: Int, isActive: Boolean): Result<Unit> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.updateTourPlanById(token ?: "", id, isActive)
            val body = response.body()

            if (response.isSuccessful && body?.status == "Success") {
                Result.Success(Unit)
            } else {
                Result.Error(message = "Gagal memperbaharui data.", throwable = null)
            }
        } catch (e: IOException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        } catch (e: HttpException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
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
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.getTourPlanPrediction(
                token ?: "",
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
                        Instant.ofEpochMilli(item.dateMillis).atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    val destinationList =
                        item.destinations.map {
                            TravelDestination(
                                name = it.name,
                                imageUrl = it.imageUrl,
                                address = it.address,
                                lat = it.lat,
                                lon = it.lon,
                                rating = it.rating,
                                categoryId = it.categoryId,
                                weekdayPrice = it.weekdayPrice,
                                weekendHolidayPrice = it.weekendHolidayPrice,
                                description = it.description,
                                id = it.id,
                            )
                        }
                    dailyList.add(
                        DailyItem(
                            number = i + 1,
                            date = date,
                            destinationList = destinationList
                        )
                    )
                }
                Result.Success(TourPlan(dailyList = dailyList))
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

    suspend fun deleteTourPlanDateDestination(
        dateId: Int,
        destinationId: Int,
    ): Result<Unit> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response =
                tourPlanApi.deleteTourPlanDateDestination(token ?: "", dateId, destinationId)
            val body = response.body()

            if (response.isSuccessful && body?.status == "Success") {
                Result.Success(Unit)
            } else {
                Result.Error(message = "Gagal menghapus data.", throwable = null)
            }
        } catch (e: IOException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        } catch (e: HttpException) {
            Timber.d(e.message)
            Result.Error(message = "Terjadi kesalahan, coba lagi nanti!", throwable = e)
        }
    }

    suspend fun markDestinationDone(
        dateId: Int,
        destinationId: Int,
    ): Result<Unit> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.markDestinationDone(token ?: "", dateId, destinationId)
            val body = response.body()
            if (response.isSuccessful && body?.status == "Success") {
                Result.Success(Unit)
            } else {
                Result.Error("Gagal memperbarui destinasi.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun markDestinationNotDone(
        dateId: Int,
        destinationId: Int,
    ): Result<Unit> {
        return try {
            val token = userPreferencesDataStore.userData.first().token
            val response = tourPlanApi.markDestinationNotDone(token ?: "", dateId, destinationId)
            val body = response.body()
            if (response.isSuccessful && body?.status == "Success") {
                Result.Success(Unit)
            } else {
                Result.Error("Gagal memperbarui destinasi.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }
}