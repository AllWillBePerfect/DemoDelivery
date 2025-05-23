package com.demo.delivery.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.demo.delivery.data.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Класс для управления пользовательскими данными с использованием DataStore.
 *
 * @property context контекст приложения, используемый для доступа к DataStore.
 */
@Singleton
class UserPreferencesUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * Поток, предоставляющий данные пользователя из DataStore.
     */
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
    /**
     * Сохраняет данные пользователя в DataStore.
     *
     * @param name имя пользователя.
     * @param birthday дата рождения пользователя.
     * @param address адрес пользователя.
     * @param apartment номер квартиры пользователя.
     * @param entrance номер подъезда пользователя.
     * @param floor этаж пользователя.
     * @param intercom код домофона пользователя.
     */
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
    /**
     * Удаляет данные пользователя из DataStore.
     */
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

    /**
     * Авторизует пользователя по email и сохраняет соответствующие данные в DataStore.
     *
     * @param email адрес электронной почты пользователя.
     */
    suspend fun authorizeByEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[IS_EMAIL_ENTER] = true
            preferences[EMAIL] = email
        }
    }

    /**
     * Авторизует пользователя по номеру телефона и сохраняет соответствующие данные в DataStore.
     *
     * @param phone номер телефона пользователя.
     */
    suspend fun authorizeByPhone(phone: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[IS_EMAIL_ENTER] = false
            preferences[PHONE] = phone
        }
    }

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"

        private val Context.dataStore by preferencesDataStore(
            name = USER_PREFERENCES_NAME
        )

        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val IS_EMAIL_ENTER = booleanPreferencesKey("is_email_enter")

        private val PHONE = stringPreferencesKey("phone")
        private val EMAIL = stringPreferencesKey("email")
        private val NAME = stringPreferencesKey("name")
        private val BIRTHDAY = stringPreferencesKey("birthday")
        private val ADDRESS = stringPreferencesKey("address")
        private val APARTMENT = stringPreferencesKey("apartment")
        private val ENTRANCE = stringPreferencesKey("entrance")
        private val FLOOR = stringPreferencesKey("floor")
        private val INTERCOM = stringPreferencesKey("intercom")

    }
}