package com.example.rollhelper3.ui.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


// Create the DataStore instance
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class DataStoreManager(private val context: Context) {

    companion object {
        // Keys for stored preferences
        private val AUDIO_ENABLED_KEY = booleanPreferencesKey("audio_enabled")
        private val PROFICIENCY_ENABLED_KEY = booleanPreferencesKey("proficiency_enabled")
        private val PROFICIENCY_VALUE_KEY = intPreferencesKey("proficiency_value")
        private val EXPERTISE_ENABLED_KEY = booleanPreferencesKey("expertise_enabled")
        private val ABILITY_MODIFIERS_KEY = stringPreferencesKey("ability_modifiers") // Stored as JSON
        private val ABILITY_ENABLED_KEY = stringPreferencesKey("ability_enabled") // Store as JSON
        private val ROLL_HISTORY_KEY = stringPreferencesKey("roll_history")

    }

    // Function to read preferences
    fun getAudioEnabled(): Flow<Boolean> = context.dataStore.data.map {
        it[AUDIO_ENABLED_KEY] ?: true // Default is true
    }

    fun getProficiencyEnabled(): Flow<Boolean> = context.dataStore.data.map {
        it[PROFICIENCY_ENABLED_KEY] ?: false
    }

    fun getProficiencyValue(): Flow<Int> = context.dataStore.data.map {
        it[PROFICIENCY_VALUE_KEY] ?: 0
    }

    fun getExpertiseEnabled(): Flow<Boolean> = context.dataStore.data.map {
        it[EXPERTISE_ENABLED_KEY] ?: false
    }

    fun getAbilityModifiers(): Flow<Map<String, Int>> = context.dataStore.data.map {
        val json = it[ABILITY_MODIFIERS_KEY] ?: "{}" // Default is empty JSON
        Json.decodeFromString(json)
    }

    fun getAbilityEnabledState(): Flow<Map<String, Boolean>> = context.dataStore.data.map {
        val json = it[ABILITY_ENABLED_KEY] ?: "{}" // Default is empty JSON
        Json.decodeFromString(json)
    }

    fun getRollHistory(): Flow<List<Triple<List<Int>, Int, Int>>> = context.dataStore.data.map {
        val json = it[ROLL_HISTORY_KEY] ?: "[]" // Default is an empty list
        Json.decodeFromString(json)
    }

    // Functions to update preferences
    suspend fun saveAudioEnabled(enabled: Boolean) {
        context.dataStore.edit { it[AUDIO_ENABLED_KEY] = enabled }
    }

    suspend fun saveProficiencyEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PROFICIENCY_ENABLED_KEY] = enabled }
    }

    suspend fun saveProficiencyValue(value: Int) {
        context.dataStore.edit { it[PROFICIENCY_VALUE_KEY] = value }
    }

    suspend fun saveExpertiseEnabled(enabled: Boolean) {
        context.dataStore.edit { it[EXPERTISE_ENABLED_KEY] = enabled }
    }

    suspend fun saveAbilityModifiers(modifiers: Map<String, Int>) {
        val json = Json.encodeToString(modifiers)
        context.dataStore.edit { it[ABILITY_MODIFIERS_KEY] = json }
    }

    suspend fun saveAbilityEnabledState(enabledState: Map<String, Boolean>) {
        val json = Json.encodeToString(enabledState)
        context.dataStore.edit { it[ABILITY_ENABLED_KEY] = json }
    }

    suspend fun saveRollHistory(history: List<Triple<List<Int>, Int, Int>>) {
        val json = Json.encodeToString(history)
        context.dataStore.edit { preferences ->
            preferences[ROLL_HISTORY_KEY] = json
        }
    }
}