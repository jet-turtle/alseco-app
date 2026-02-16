package kz.rusmen.alseco.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.round
import kotlin.math.roundToInt

class AlsecoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AlsecoInputUiState())
    val uiState = _uiState.asStateFlow()

    fun updateField(field: Field, newValue: String) {
        _uiState.update { currentState ->
            when (field) {
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

    fun calculateWaterInPayment(last: String, prev: String, rates: String): Double {
        val splitRates = rates.trim().split("\\s+".toRegex())

        val rate1 = splitRates.getOrNull(0)?.toDoubleOrNull() ?: 0.0
        val rate2 = splitRates.getOrNull(1)?.toDoubleOrNull() ?: rate1
        val rate3 = splitRates.getOrNull(2)?.toDoubleOrNull() ?: rate2
        val rate4 = splitRates.getOrNull(3)?.toDoubleOrNull() ?: rate3

        val lastVal = last.toDoubleOrNull() ?: 0.0
        val prevVal = prev.toDoubleOrNull() ?: 0.0
        val volume = lastVal - prevVal

        if (volume <= 0) return 0.0
        var payment = 0.0

        when {
            volume <= 12.0 -> {
                val sum = volume * rate1
                payment += sum
            }
            volume in 13.0..20.0 -> {
                payment += round(12.0 * rate1 * 100) / 100

                val sum = (volume - 12.0) * rate2
                payment += sum
            }
            volume in 21.0..40.0 -> {
                payment += round(12.0 * rate1 * 100) / 100
                payment += round(8.0 * rate2 * 100) / 100

                val sum = (volume - 20.0) * rate3
                payment += sum
            }
            else -> {
                payment += round(12.0 * rate1 * 100) / 100
                payment += round(8.0 * rate2 * 100) / 100
                payment += round(20.0 * rate3 * 100) / 100

                val sum = (volume - 40.0) * rate4
                payment += sum
            }
        }
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
}
