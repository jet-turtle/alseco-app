package kz.rusmen.alseco.ui

data class AlsecoInputUiState(
    val powerRateInput: String = "",
    val powerLastInput: String = "",
    val powerPrevInput: String = "",
    val waterInRateInput: String = "",
    val waterInLastInput: String = "",
    val waterInPrevInput: String = "",
    val waterOutRateInput: String = "",
    val gasRateInput: String = "",
    val gasLastInput: String = "",
    val gasPrevInput: String = "",
    val vdgoRateInput: String = "",
    val tboRateInput: String = "",
    val landCessRateInput: String = "",
    val updatedAt: Long = 0L
)
