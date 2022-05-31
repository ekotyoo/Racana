package com.ekotyoo.racana.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.ekotyoo.racana.data.model.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    val userData = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception.message)
                emit(emptyPreferences())
            }
        }
        .map { preferences ->
            UserModel(
                preferences[KEY_USER_ID],
                preferences[KEY_USER_NAME],
                preferences[KEY_USER_EMAIL],
                preferences[KEY_USER_TOKEN]
            )
        }

    suspend fun saveUserData(user: UserModel) {
        context.dataStore.edit { preferences ->
            user.id?.let { preferences[KEY_USER_ID] = it }
            user.name?.let { preferences[KEY_USER_NAME] = it }
            user.email?.let { preferences[KEY_USER_EMAIL] = it }
            user.token?.let { preferences[KEY_USER_TOKEN] = it }
        }
    }

    suspend fun deleteUserData() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USER_NAME)
            preferences.remove(KEY_USER_EMAIL)
            preferences.remove(KEY_USER_TOKEN)
        }
    }

    private companion object {
        val KEY_USER_ID = intPreferencesKey("KEY_ID")
        val KEY_USER_NAME = stringPreferencesKey("KEY_NAME")
        val KEY_USER_EMAIL = stringPreferencesKey("KEY_EMAIL")
        val KEY_USER_TOKEN = stringPreferencesKey("KEY_TOKEN")
    }
}