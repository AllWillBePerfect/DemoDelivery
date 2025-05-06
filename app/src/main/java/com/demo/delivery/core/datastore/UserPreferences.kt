package com.demo.delivery.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.demo.delivery.core.datastore.model.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val userDataFlow: Flow<UserData> = context.dataStore.data.map { preferences ->
        UserData(
            isLoggedIn = preferences[IS_LOGGED_IN] == true,
            isEmailEnter = preferences[IS_EMAIL_ENTER] == true,
            phone = preferences[PHONE] ?: "",
            email = preferences[EMAIL] ?: "",
            name = preferences[NAME] ?: "",
            birthday = preferences[BIRTHDAY] ?: "",
            address = preferences[ADDRESS] ?: "",
            apartment = preferences[APARTMENT] ?: "",
            entrance = preferences[ENTRANCE] ?: "",
            floor = preferences[FLOOR] ?: "",
            intercom = preferences[INTERCOM] ?: "",
        )
    }

    suspend fun saveUserData(
        name: String,
        birthday: String,
        address: String,
        apartment: String,
        entrance: String,
        floor: String,
        intercom: String,

    ) {
        context.dataStore.edit { preferences ->
            preferences[NAME] = name
            preferences[BIRTHDAY] = birthday
            preferences[ADDRESS] = address
            preferences[APARTMENT] = apartment
            preferences[ENTRANCE] = entrance
            preferences[FLOOR] = floor
            preferences[INTERCOM] = intercom
        }
    }

    suspend fun deleteUserData() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
            preferences[IS_EMAIL_ENTER] = false
            preferences[PHONE] = ""
            preferences[EMAIL] = ""
            preferences[NAME] = ""
            preferences[BIRTHDAY] = ""
            preferences[ADDRESS] = ""
            preferences[APARTMENT] = ""
            preferences[ENTRANCE] = ""
            preferences[FLOOR] = ""
            preferences[INTERCOM] = ""
        }
    }

    suspend fun authorizeByEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[IS_EMAIL_ENTER] = true
            preferences[EMAIL] = email
        }
    }

    suspend fun authorizeByPhone(phone: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[IS_EMAIL_ENTER] = false
            preferences[PHONE] = phone
        }
    }

    fun userIsLoggedIn() = userDataFlow.map { it.isLoggedIn }


    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"

        private val Context.dataStore by preferencesDataStore(
            name = USER_PREFERENCES_NAME
        )

        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val IS_EMAIL_ENTER = booleanPreferencesKey("is_email_enter")

        val PHONE = stringPreferencesKey("phone")
        val EMAIL = stringPreferencesKey("email")
        val NAME = stringPreferencesKey("name")
        val BIRTHDAY = stringPreferencesKey("birthday")
        val ADDRESS = stringPreferencesKey("address")
        val APARTMENT = stringPreferencesKey("apartment")
        val ENTRANCE = stringPreferencesKey("entrance")
        val FLOOR = stringPreferencesKey("floor")
        val INTERCOM = stringPreferencesKey("intercom")

    }
}