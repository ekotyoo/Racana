package com.ekotyoo.racana.data.datasource.local

import com.ekotyoo.racana.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserPreferencesDataStore @Inject constructor() {

    fun getUserData(): Flow<UserModel> = flow {
        // TODO: Get user data from DataStore 
        emit(UserModel("Id", "Name", "Email", "Token"))
    }

    suspend fun saveUserData(user: UserModel) {
        // TODO: Save user data to DataStore
    }
}