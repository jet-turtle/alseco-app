package kz.rusmen.alseco.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.rusmen.alseco.R
import kz.rusmen.alseco.ui.theme.ALSECOTheme
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun AlsecoLayout(
    viewModel: AlsecoViewModel = viewModel(factory = AlsecoViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    val powerPayment = viewModel.calculatePayment(uiState.powerLastInput, uiState.powerPrevInput, uiState.powerRateInput)
    val waterInPayment = viewModel.calculateWaterInPayment(uiState.waterInLastInput, uiState.waterInPrevInput, uiState.waterInRateInput)
    val waterOutPayment = viewModel.calculatePayment(uiState.waterInLastInput, uiState.waterInPrevInput, uiState.waterOutRateInput)
    val gasPayment = viewModel.calculatePayment(uiState.gasLastInput, uiState.gasPrevInput, uiState.gasRateInput)

    val vdgoRate = uiState.vdgoRateInput.toDoubleOrNull() ?: 0.0
    val vdgoPayment = vdgoRate

    val tboRate = uiState.tboRateInput.toDoubleOrNull() ?: 0.0
    val tboPayment = tboRate

    val landCessRate = uiState.landCessRateInput.toDoubleOrNull() ?: 0.0
    val landCessPayment = landCessRate

    val totalPayment = round((powerPayment + waterInPayment + waterOutPayment + gasPayment + vdgoPayment + tboPayment + landCessPayment) * 100) / 100

    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.background))
            .statusBarsPadding()
            .padding(horizontal = 4.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.alseco_utilities),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.5.sp,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
//                        Color.Red,
//                        colorResource(R.color.gold),
//                        colorResource(R.color.green),
                        colorResource(R.color.teal_700),
                        colorResource(R.color.teal_200),
                        colorResource(R.color.ic_launcher_background),
//                        Color.Cyan,
//                        Color.Blue,
//                        Color.Magenta,
                    )
                )
            ) ,
            modifier = Modifier
                .padding(16.dp)
        )

        MeterBlock(
            title = R.string.power,
            titleColor = R.color.green,
            rateInput = uiState.powerRateInput,
            lastInput = uiState.powerLastInput,
            prevInput = uiState.powerPrevInput,
            amount = viewModel.calculateAmount(uiState.powerLastInput, uiState.powerPrevInput),
            payment = viewModel.calculatePayment(uiState.powerLastInput, uiState.powerPrevInput, uiState.powerRateInput),
            onRateInputChange = { newValue ->
                viewModel.updateField(Field.POWER_RATE, newValue)
            },
            onLastInputChange = { newValue ->
                viewModel.updateField(Field.POWER_LAST, newValue)
            },
            onPrevInputChange = { newValue ->
                viewModel.updateField(Field.POWER_PREV, newValue)
            }
        )
        MeterBlock(
            title = R.string.water_in,
            titleColor = R.color.blue,
            rateInput = uiState.waterInRateInput,
            lastInput = uiState.waterInLastInput,
            prevInput = uiState.waterInPrevInput,
            amount = viewModel.calculateAmount(uiState.waterInLastInput, uiState.waterInPrevInput),
            payment = viewModel.calculateWaterInPayment(uiState.waterInLastInput, uiState.waterInPrevInput, uiState.waterInRateInput),
            onRateInputChange = { newValue ->
                viewModel.updateField(Field.WATER_IN_RATE, newValue)
            },
            onLastInputChange = { newValue ->
                viewModel.updateField(Field.WATER_IN_LAST, newValue)
            },
            onPrevInputChange = { newValue ->
                viewModel.updateField(Field.WATER_IN_PREV, newValue)
            }
        )
        MeterBlock(
            title = R.string.water_out,
            titleColor = R.color.blue,
            rateInput = uiState.waterOutRateInput,
            lastInput = uiState.waterInLastInput,
            prevInput = uiState.waterInPrevInput,
            amount = viewModel.calculateAmount(uiState.waterInLastInput, uiState.waterInPrevInput),
            payment = viewModel.calculatePayment(uiState.waterInLastInput, uiState.waterInPrevInput, uiState.waterOutRateInput),
            onRateInputChange = { newValue ->
                viewModel.updateField(Field.WATER_OUT_RATE, newValue)
            },
            onLastInputChange = { newValue ->
                viewModel.updateField(Field.WATER_OUT_RATE, newValue)
            },
            onPrevInputChange = { newValue ->
                viewModel.updateField(Field.WATER_OUT_RATE, newValue)
            }
        )
        MeterBlock(
            title = R.string.gas,
            titleColor = R.color.orange,
            rateInput = uiState.gasRateInput,
            lastInput = uiState.gasLastInput,
            prevInput = uiState.gasPrevInput,
            amount = viewModel.calculateAmount(uiState.gasLastInput, uiState.gasPrevInput),
            payment = viewModel.calculatePayment(uiState.gasLastInput, uiState.gasPrevInput, uiState.gasRateInput),
            onRateInputChange = { newValue ->
                viewModel.updateField(Field.GAS_RATE, newValue)
            },
            onLastInputChange = { newValue ->
                viewModel.updateField(Field.GAS_LAST, newValue)
            },
            onPrevInputChange = { newValue ->
                viewModel.updateField(Field.GAS_PREV, newValue)
            }
        )
        SingleBlock(
            title = R.string.vdgo,
            rateInput = uiState.vdgoRateInput,
            onValueChange = { newValue ->
                viewModel.updateField(Field.VDGO_RATE, newValue)
            }
        )
        SingleBlock(
            title = R.string.tbo,
            rateInput = uiState.tboRateInput,
            onValueChange = { newValue ->
                viewModel.updateField(Field.TBO_RATE, newValue)
            }
        )
        SingleBlock(
            title = R.string.land_cess,
            rateInput = uiState.landCessRateInput,
            onValueChange = { newValue ->
                viewModel.updateField(Field.LAND_CESS_RATE, newValue)
            }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 280.dp)
        ) {
            Button(
                onClick = { viewModel.clear() },
                modifier = Modifier
                    .height(36.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "clear",
                    fontSize = 18.sp,
                    color = Color.Yellow,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            }
            Text(
                text = stringResource(R.string.total, totalPayment.toString()),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = colorResource(R.color.teal_700)
            )
        }
    }
}

@Composable
fun MeterBlock(
    title: Int,
    titleColor: Int,
    rateInput: String,
    lastInput: String,
    prevInput: String,
    amount: Int,
    payment: Double,
    onRateInputChange: (String) -> Unit,
    onLastInputChange: (String) -> Unit,
    onPrevInputChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(titleColor),
                modifier = Modifier
                    .weight(1.0f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .padding(8.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            TextField(
                value = rateInput,
                singleLine = true,
                onValueChange = onRateInputChange,
                label = { Text(stringResource(R.string.rate)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .weight(1.0f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .fillMaxHeight()
            )
            Text(
                text = amount.toString(),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal_700),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(0.9f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .padding(8.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = lastInput,
                singleLine = true,
                onValueChange = onLastInputChange,
                label = { Text(stringResource(R.string.last)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .weight(1.0f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .fillMaxHeight()
            )
            TextField(
                value = prevInput,
                singleLine = true,
                onValueChange = onPrevInputChange,
                label = { Text(stringResource(R.string.previous)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .weight(1.0f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .fillMaxHeight()
            )
            Text(
                text = payment.toString(),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal_700),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(0.9f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .padding(8.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun SingleBlock(
    title: Int,
    rateInput: String,
    onValueChange: (String) -> Unit,
) {
    val rateInputDouble = rateInput.replace(',', '.').toDoubleOrNull() ?: 0.0
    Column(
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal_700),
                modifier = Modifier
                    .weight(1.0f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .padding(8.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            TextField(
                value = rateInput,
                singleLine = true,
                onValueChange = onValueChange,
                label = { Text(stringResource(R.string.rate)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .weight(1.0f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .fillMaxHeight()
            )
            Text(
                text = if (rateInput.isBlank()) "" else rateInputDouble.toString(),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal_700),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(0.9f)
                    .border(width = 1.dp, color = colorResource(R.color.teal_700))
                    .padding(8.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ALSECOPreview() {
    ALSECOTheme {
        AlsecoLayout()
    }
}
