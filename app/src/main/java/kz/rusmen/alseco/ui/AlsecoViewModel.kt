package kz.rusmen.alseco.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.rusmen.alseco.AlsecoApplication
import kz.rusmen.alseco.data.UserPreferencesRepository
import java.time.Instant
import java.time.ZoneId
import kotlin.math.round
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
class AlsecoViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AlsecoInputUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferences
                .take(1)
                .collect { savedData ->
                    val processedData = checkAndRotateMonth(savedData)
                    _uiState.value = processedData
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkAndRotateMonth(state: AlsecoInputUiState): AlsecoInputUiState {
        if (state.updatedAt == 0L) return state

        val zone = ZoneId.systemDefault()
        val lastUpdate = Instant.ofEpochMilli(state.updatedAt).atZone(zone)
        val now = Instant.now().atZone(zone)

        val isNewMonth = lastUpdate.month != now.month || lastUpdate.year != now.year

        return if (isNewMonth) {
            state.copy(
                powerPrevInput = state.powerLastInput,
                powerLastInput = "",

                waterInPrevInput = state.waterInLastInput,
                waterInLastInput = "",

                gasPrevInput = state.gasLastInput,
                gasLastInput = "",

                updatedAt = System.currentTimeMillis()
            )
        } else {
            state
        }
    }

    fun saveStateDataStore() {
        viewModelScope.launch {
            userPreferencesRepository.saveUserPreference(_uiState.value)
        }
    }

    fun updateField(field: Field, newValue: String) {
        _uiState.update { currentState ->
            when (field) {
                Field.OCCUPANTS -> currentState.copy(occupantsInput = newValue)
                Field.POWER_RATE -> currentState.copy(powerRateInput = newValue)
                Field.POWER_LAST -> currentState.copy(powerLastInput = newValue)
                Field.POWER_PREV -> currentState.copy(powerPrevInput = newValue)
                Field.WATER_IN_RATE -> currentState.copy(waterInRateInput = newValue)
                Field.WATER_IN_LAST -> currentState.copy(waterInLastInput = newValue)
                Field.WATER_IN_PREV -> currentState.copy(waterInPrevInput = newValue)
                Field.WATER_OUT_RATE -> currentState.copy(waterOutRateInput = newValue)
                Field.GAS_RATE -> currentState.copy(gasRateInput = newValue)
                Field.GAS_LAST -> currentState.copy(gasLastInput = newValue)
                Field.GAS_PREV -> currentState.copy(gasPrevInput = newValue)
                Field.VDGO_RATE -> currentState.copy(vdgoRateInput = newValue)
                Field.TBO_RATE -> currentState.copy(tboRateInput = newValue)
                Field.LAND_CESS_RATE -> currentState.copy(landCessRateInput = newValue)
            }
        }
    }

    fun calculateAmount(last: String, prev: String): Int {
        val amount = (last.toDoubleOrNull() ?: 0.0) - (prev.toDoubleOrNull() ?: 0.0)
        val amountInt = if (amount < 0) 0 else amount.roundToInt()

        return amountInt
    }

    fun calculatePayment(last: String, prev: String, rate: String): Double {
        var payment = ((last.toDoubleOrNull() ?: 0.0) - (prev.toDoubleOrNull() ?: 0.0)) * (rate.toDoubleOrNull() ?: 0.0)
        payment = if (payment < 0) 0.0 else round(payment * 100) / 100

        return payment
    }

    fun calculateWaterInPayment(last: String, prev: String, rates: String, occupants: String): Double {

        val personAmount = occupants.toIntOrNull() ?: 1
        val splitRates = rates.trim().split("\\s+".toRegex())

        val rate1 = splitRates.getOrNull(0)?.toDoubleOrNull() ?: 0.0
        val rate2 = splitRates.getOrNull(1)?.toDoubleOrNull() ?: rate1
        val rate3 = splitRates.getOrNull(2)?.toDoubleOrNull() ?: rate2
        val rate4 = splitRates.getOrNull(3)?.toDoubleOrNull() ?: rate3

        val lastVal = last.toDoubleOrNull() ?: 0.0
        val prevVal = prev.toDoubleOrNull() ?: 0.0
        val volume = lastVal - prevVal

        if (volume <= 0) return 0.0

        var level1 = 0.0
        var level2 = 0.0
        var level3 = 0.0
        var level4 = 0.0

        if (volume > personAmount * 10) {
            level1 = personAmount * 3 * rate1
            level2 = ((personAmount * 5) - (personAmount * 3)) * rate2
            level3 = ((personAmount * 10) - (personAmount * 5)) * rate3
            level4 = (volume - personAmount * 10) * rate4
        } else if (volume > personAmount * 5) {
            level1 = personAmount * 3 * rate1
            level2 = ((personAmount * 5) - (personAmount * 3)) * rate2
            level3 = (volume - personAmount * 5) * rate3
        } else if (volume > 3) {
            level1 = personAmount * 3 * rate1
            level2 = (volume - 3) * rate2
        } else {
            level1 = volume * rate1
        }

        var payment = level1 + level2 + level3 + level4
        payment = round(payment * 100) / 100

        return payment
    }

    fun calculateMultiRatePayment(last: String, prev: String, rates: String, occupants: String): Double {

        val personAmount = occupants.toIntOrNull() ?: 1
        val splitRates = rates.trim().split("\\s+".toRegex())

        val rate1 = splitRates.getOrNull(0)?.toDoubleOrNull() ?: 0.0
        val rate2 = splitRates.getOrNull(1)?.toDoubleOrNull() ?: rate1
        val rate3 = splitRates.getOrNull(2)?.toDoubleOrNull() ?: rate2

        val lastVal = last.toDoubleOrNull() ?: 0.0
        val prevVal = prev.toDoubleOrNull() ?: 0.0
        val volume = lastVal - prevVal

        if (volume <= 0) return 0.0

        var level1 = 0.0
        var level2 = 0.0
        var level3 = 0.0

        if (volume > personAmount * 160) {
            level1 = personAmount * 90 * rate1
            level2 = ((personAmount * 160) - (personAmount * 90)) * rate2
            level3 = (volume - personAmount * 160) * rate3
        } else if (volume > personAmount * 90) {
            level1 = personAmount * 90 * rate1
            level2 = (volume - personAmount * 90) * rate2
        } else {
            level1 = volume * rate1
        }

        var payment = level1 + level2 + level3
        payment = round(payment * 100) / 100

        return payment
    }

    fun clear() {
        _uiState.update { currentState ->
            currentState.copy(
                powerLastInput = "",
                powerPrevInput = "",
                waterInLastInput = "",
                waterInPrevInput = "",
                gasLastInput = "",
                gasPrevInput = ""
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlsecoApplication)
                AlsecoViewModel(application.userPreferencesRepository)
            }
        }
    }
}
