package com.ekotyoo.racana.data.repository

import com.ekotyoo.racana.data.datasource.local.UserPreferencesDataStore
import com.ekotyoo.racana.data.datasource.remote.AuthApi
import com.ekotyoo.racana.data.model.UserModel
import dagger.hilt.android.scopes.ViewModelScoped
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val userPreferencesDataStore: UserPreferencesDataStore
) {
    val userData = userPreferencesDataStore.getUserData()

    suspend fun login(email: String, password: String) {
        try {
            val response = authApi.login(email, password)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                userPreferencesDataStore.saveUserData(
                    UserModel(
                        body.user.id,
                        body.user.name,
                        body.user.email,
                        body.token
                    )
                )
                Timber.d("Success: $body")
            }
        } catch (e: Exception) {
            Timber.d("Error: " + e.message)
        }
    }
}