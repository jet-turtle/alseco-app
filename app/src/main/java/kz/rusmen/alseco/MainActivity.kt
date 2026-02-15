package kz.rusmen.alseco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import kz.rusmen.alseco.ui.theme.ALSECOTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALSECOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AlsecoLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun AlsecoLayout() {
    var powerRateInput by rememberSaveable { mutableStateOf("28.07") }
    val powerRate = powerRateInput.toDoubleOrNull() ?: 0.0
    var powerLastInput by rememberSaveable { mutableStateOf("") }
    val powerLast = powerLastInput.toDoubleOrNull() ?: 0.0
    var powerPrevInput by rememberSaveable { mutableStateOf("") }
    val powerPrev = powerPrevInput.toDoubleOrNull() ?: 0.0
    val powerAmount = CalculateAmount(powerLast, powerPrev)
    val powerPayment = CalculatePayment(powerLast, powerPrev, powerRate)

    var waterInRateInput by rememberSaveable { mutableStateOf("103 123.6 154.49 205.99") }
    val waterInRate = waterInRateInput
    var waterInLastInput by rememberSaveable { mutableStateOf("") }
    val waterInLast = waterInLastInput.toDoubleOrNull() ?: 0.0
    var waterInPrevInput by rememberSaveable { mutableStateOf("") }
    val waterInPrev = waterInPrevInput.toDoubleOrNull() ?: 0.0
    val waterInAmount = CalculateAmount(waterInLast, waterInPrev)
    val waterInPayment = CalculateWaterInPayment(waterInLast, waterInPrev, waterInRate)

    var waterOutRateInput by rememberSaveable { mutableStateOf("60.82") }
    val waterOutRate = waterOutRateInput.toDoubleOrNull() ?: 0.0
    var waterOutLastInput = waterInLastInput
    var waterOutPrevInput = waterInPrevInput
    val waterOutAmount = CalculateAmount(waterInLast, waterInPrev)
    val waterOutPayment = CalculatePayment(waterInLast, waterInPrev, waterOutRate)

    var gasRateInput by rememberSaveable { mutableStateOf("37.30937") }
    val gasRate = gasRateInput.toDoubleOrNull() ?: 0.0
    var gasLastInput by rememberSaveable { mutableStateOf("") }
    val gasLast = gasLastInput.toDoubleOrNull() ?: 0.0
    var gasPrevInput by rememberSaveable { mutableStateOf("") }
    val gasPrev = gasPrevInput.toDoubleOrNull() ?: 0.0
    val gasAmount = CalculateAmount(gasLast, gasPrev)
    val gasPayment = CalculatePayment(gasLast, gasPrev, gasRate)

    var vdgoRateInput by rememberSaveable { mutableStateOf("320") }
    val vdgoRate = vdgoRateInput.toDoubleOrNull() ?: 0.0
    val vdgoPayment = vdgoRate

    var tboRateInput by rememberSaveable { mutableStateOf("2874") }
    val tboRate = tboRateInput.toDoubleOrNull() ?: 0.0
    val tboPayment = tboRate

    var landCessRateInput by rememberSaveable { mutableStateOf("0") }
    val landCessRate = landCessRateInput.toDoubleOrNull() ?: 0.0
    val landCessPayment = landCessRate

    val totalPayment = round((powerPayment + waterInPayment + waterOutPayment + gasPayment + vdgoPayment + tboPayment + landCessPayment) * 100) / 100

    fun clearInputs() {
        powerLastInput = ""
        powerPrevInput = ""
        waterInLastInput = ""
        waterInPrevInput = ""
        gasLastInput = ""
        gasPrevInput = ""
        vdgoRateInput = "0"
        tboRateInput = "2874"
        landCessRateInput = "0"
    }

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
                    text = stringResource(R.string.power),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.green),
                    modifier = Modifier
                        .weight(1.0f)
                        .border(width = 1.dp, color = colorResource(R.color.teal_700))
                        .padding(8.dp)
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                TextField(
                    value = powerRateInput,
                    singleLine = true,
                    onValueChange = {powerRateInput = it},
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
                    text = powerAmount.toString(),
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
                    value = powerLastInput,
                    singleLine = true,
                    onValueChange = {powerLastInput = it},
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
                    value = powerPrevInput,
                    singleLine = true,
                    onValueChange = {powerPrevInput = it},
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
                    text = powerPayment.toString(),
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
                    text = stringResource(R.string.water_in),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.blue),
                    modifier = Modifier
                        .weight(1.0f)
                        .border(width = 1.dp, color = colorResource(R.color.teal_700))
                        .padding(8.dp)
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                TextField(
                    value = waterInRateInput,
                    singleLine = true,
                    onValueChange = {waterInRateInput = it},
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
                    text = waterInAmount.toString(),
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
                    value = waterInLastInput,
                    singleLine = true,
                    onValueChange = {waterInLastInput = it},
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
                    value = waterInPrevInput,
                    singleLine = true,
                    onValueChange = {waterInPrevInput = it},
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
                    text = waterInPayment.toString(),
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
                    text = stringResource(R.string.water_out),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.blue),
                    modifier = Modifier
                        .weight(1.0f)
                        .border(width = 1.dp, color = colorResource(R.color.teal_700))
                        .padding(8.dp)
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                TextField(
                    value = waterOutRateInput,
                    singleLine = true,
                    onValueChange = {waterOutRateInput = it},
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
                    text = waterOutAmount.toString(),
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
                    value = waterOutLastInput,
                    singleLine = true,
                    onValueChange = {waterOutLastInput = it},
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
                    value = waterOutPrevInput,
                    singleLine = true,
                    onValueChange = {waterOutPrevInput = it},
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
                    text = waterOutPayment.toString(),
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
                    text = stringResource(R.string.gas),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.orange),
                    modifier = Modifier
                        .weight(1.0f)
                        .border(width = 1.dp, color = colorResource(R.color.teal_700))
                        .padding(8.dp)
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                TextField(
                    value = gasRateInput,
                    singleLine = true,
                    onValueChange = {gasRateInput = it},
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
                    text = gasAmount.toString(),
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
                    value = gasLastInput,
                    singleLine = true,
                    onValueChange = {gasLastInput = it},
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
                    value = gasPrevInput,
                    singleLine = true,
                    onValueChange = {gasPrevInput = it},
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
                    text = gasPayment.toString(),
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
                    text = stringResource(R.string.vdgo),
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
                    value = vdgoRateInput,
                    singleLine = true,
                    onValueChange = {vdgoRateInput = it},
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
                    text = vdgoPayment.toString(),
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
                    text = stringResource(R.string.tbo),
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
                    value = tboRateInput,
                    singleLine = true,
                    onValueChange = {tboRateInput = it},
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
                    text = tboPayment.toString(),
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

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.land_cess),
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
                    value = landCessRateInput,
                    singleLine = true,
                    onValueChange = {landCessRateInput = it},
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
                    text = landCessPayment.toString(),
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

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 20.dp, end = 8.dp, bottom = 280.dp)
        ) {
            Button(
                onClick = { clearInputs() },
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