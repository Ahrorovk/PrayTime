package com.ahrorovk.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context:Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        val TASBIH_COUNTER_STATE = intPreferencesKey("tasbih_counter_state")
    }
    suspend fun updateTasbihCounterState(state: Int) {
        context.dataStore.edit { preferences ->
            preferences[TASBIH_COUNTER_STATE] = state
        }
    }
    val getTasbihCounterState = context.dataStore.data.map {
        it[TASBIH_COUNTER_STATE] ?: 0
    }
}