package kz.rusmen.alseco.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kz.rusmen.alseco.ui.AlsecoInputUiState
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val POWER_RATE = stringPreferencesKey("power_rate")
        val POWER_LAST = stringPreferencesKey("power_last")
        val POWER_PREV = stringPreferencesKey("power_prev")
        val WATER_IN_RATE = stringPreferencesKey("water_in_rate")
        val WATER_IN_LAST = stringPreferencesKey("water_in_last")
        val WATER_IN_PREV = stringPreferencesKey("water_in_prev")
        val WATER_OUT_RATE = stringPreferencesKey("water_out_rate")
        val GAS_RATE = stringPreferencesKey("gas_rate")
        val GAS_LAST = stringPreferencesKey("gas_last")
        val GAS_PREV = stringPreferencesKey("gas_prev")
        val VDGO_RATE = stringPreferencesKey("vdgo_rate")
        val TBO_RATE = stringPreferencesKey("tbo_rate")
        val LAND_CESS_RATE = stringPreferencesKey("land_cess_rate")
        val UPDATED_AT = longPreferencesKey("updated_at")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveUserPreference(alsecoInputUiState: AlsecoInputUiState) {
        dataStore.edit { preferences ->
            preferences[POWER_RATE] = alsecoInputUiState.powerRateInput
            preferences[POWER_LAST] = alsecoInputUiState.powerLastInput
            preferences[POWER_PREV] = alsecoInputUiState.powerPrevInput
            preferences[WATER_IN_RATE] = alsecoInputUiState.waterInRateInput
            preferences[WATER_IN_LAST] = alsecoInputUiState.waterInLastInput
            preferences[WATER_IN_PREV] = alsecoInputUiState.waterInPrevInput
            preferences[WATER_OUT_RATE] = alsecoInputUiState.waterOutRateInput
            preferences[GAS_RATE] = alsecoInputUiState.gasRateInput
            preferences[GAS_LAST] = alsecoInputUiState.gasLastInput
            preferences[GAS_PREV] = alsecoInputUiState.gasPrevInput
            preferences[VDGO_RATE] = alsecoInputUiState.vdgoRateInput
            preferences[TBO_RATE] = alsecoInputUiState.tboRateInput
            preferences[LAND_CESS_RATE] = alsecoInputUiState.landCessRateInput
            preferences[UPDATED_AT] = System.currentTimeMillis()
        }
    }

    val userPreferences: Flow<AlsecoInputUiState> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            AlsecoInputUiState(
                powerRateInput = preferences[POWER_RATE] ?: "",
                powerLastInput = preferences[POWER_LAST] ?: "",
                powerPrevInput = preferences[POWER_PREV] ?: "",
                waterInRateInput = preferences[WATER_IN_RATE] ?: "",
                waterInLastInput = preferences[WATER_IN_LAST] ?: "",
                waterInPrevInput = preferences[WATER_IN_PREV] ?: "",
                waterOutRateInput = preferences[WATER_OUT_RATE] ?: "",
                gasRateInput = preferences[GAS_RATE] ?: "",
                gasLastInput = preferences[GAS_LAST] ?: "",
                gasPrevInput = preferences[GAS_PREV] ?: "",
                vdgoRateInput = preferences[VDGO_RATE] ?: "",
                tboRateInput = preferences[TBO_RATE] ?: "",
                landCessRateInput = preferences[LAND_CESS_RATE] ?: "",
                updatedAt = preferences[UPDATED_AT] ?: 0L
            )
        }
}
