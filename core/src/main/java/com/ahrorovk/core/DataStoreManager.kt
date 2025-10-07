package com.ahrorovk.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        val TASBIH_COUNTER_STATE = intPreferencesKey("tasbih_counter_state")
        val DATE_STATE = longPreferencesKey("date_state")
        val LATITUDE_STATE = doublePreferencesKey("latitude_state")
        val LONGITUDE_STATE = doublePreferencesKey("longitude_state")
        val LANGUAGE_STATE = intPreferencesKey("language_state")
    }

    suspend fun updateTasbihCounterState(state: Int) {
        context.dataStore.edit { preferences ->
            preferences[TASBIH_COUNTER_STATE] = state
        }
    }

    suspend fun updateLatitudeState(lat: Double) {
        context.dataStore.edit { preferences ->
            preferences[LATITUDE_STATE] = lat
        }
    }

    suspend fun updateLongitudeState(long: Double) {
        context.dataStore.edit { preferences ->
            preferences[LONGITUDE_STATE] = long
        }
    }

    suspend fun updateLanguageState(state: Int) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_STATE] = state
        }
    }

    suspend fun updateDateState(state: Long) {
        context.dataStore.edit { preferences ->
            preferences[DATE_STATE] = state
        }
    }

    val getLatitudeState = context.dataStore.data.map {
        it[LATITUDE_STATE] ?: 0.0
    }

    val getLongitudeState = context.dataStore.data.map {
        it[LONGITUDE_STATE] ?: 0.0
    }

    val getTasbihCounterState = context.dataStore.data.map {
        it[TASBIH_COUNTER_STATE] ?: 0
    }
    val getDateState = context.dataStore.data.map {
        it[DATE_STATE] ?: 0
    }

    val getLanguageState = context.dataStore.data.map {
        it[LANGUAGE_STATE] ?: 0
    }
}