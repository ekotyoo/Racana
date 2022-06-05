package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.core.utils.Result
import com.ekotyoo.racana.data.datasource.local.UserPreferencesDataStore
import com.ekotyoo.racana.data.datasource.remote.api.DestinationApi
import com.ekotyoo.racana.data.model.TravelDestination
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class DestinationRepository @Inject constructor(
    private val destinationApi: DestinationApi,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) {

    suspend fun getDestinations(query: String? = null, category: Int? = null): Result<List<TravelDestination>> {
        try {
            val token = userPreferencesDataStore.userData.first().token
            val response = destinationApi.searchDestination(token ?: "", query, category)
            val data = response.body()?.data
            return if (response.isSuccessful && data != null) {
                val destinations = data.map {
                    TravelDestination(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrl,
                        address = it.addresss,
                        city = it.city,
                        lat = it.lat,
                        lon = it.lon,
                        description = it.description,
                        weekdayPrice = it.weekdayPrice,
                        weekendHolidayPrice = it.weekendHolidayPrice,
                        rating = it.rating,
                        categoryId = it.categoryId
                    )
                }
                Result.Success(destinations)
            } else {
                Result.Error("Failed getting data.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun getTopDestinations(limit: Int? = null): Result<List<TravelDestination>> {
        try {
            val token = userPreferencesDataStore.userData.first().token
            val response = destinationApi.getTopDestinations(token ?: "", limit)
            val data = response.body()?.data
            return if (response.isSuccessful && data != null) {
                val destinations = data.map {
                    TravelDestination(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrl,
                        address = it.addresss,
                        city = it.city,
                        lat = it.lat,
                        lon = it.lon,
                        description = it.description,
                        weekdayPrice = it.weekdayPrice,
                        weekendHolidayPrice = it.weekendHolidayPrice,
                        rating = it.rating,
                        categoryId = it.categoryId
                    )
                }
                Result.Success(destinations)
            } else {
                Result.Error("Failed getting data.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }

    suspend fun getDestinationById(id: Int): Result<TravelDestination> {
        try {
            val token = userPreferencesDataStore.userData.first().token
            val response = destinationApi.getDestinationById(token ?: "", id)
            val data = response.body()?.data
            return if (response.isSuccessful && data != null) {
                val destination = TravelDestination(
                    id = data.id,
                    name = data.name,
                    imageUrl = data.imageUrl,
                    address = data.address,
                    lat = data.lat,
                    lon = data.lon,
                    description = data.description,
                    weekdayPrice = data.weekdayPrice,
                    weekendHolidayPrice = data.weekendHolidayPrice,
                    rating = data.rating,
                    categoryId = data.categoryId
                )
                Result.Success(destination)
            } else {
                Result.Error("Failed getting data.", null)
            }
        } catch (e: IOException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        } catch (e: HttpException) {
            Timber.d(e)
            return Result.Error("Terjadi kesalahan, coba lagi nanti.", null)
        }
    }
}