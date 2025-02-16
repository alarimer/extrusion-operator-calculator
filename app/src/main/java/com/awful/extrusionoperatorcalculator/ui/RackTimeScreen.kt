package com.awful.extrusionoperatorcalculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awful.extrusionoperatorcalculator.R
import com.awful.extrusionoperatorcalculator.data.DataSource
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

@Serializable
object RackTimeScreen

@Composable
fun RackTimeScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pullerSpeed by remember { mutableStateOf("2.5") }
    var isErrorPS by remember { mutableStateOf(false) }
    var currentLength by remember { mutableStateOf("252") }
    var isErrorCL by remember { mutableStateOf(false) }
    var currentFraction: String by remember { mutableStateOf("0") }
    var piecesPerRack by remember { mutableStateOf("60") }
    var isErrorPPR by remember { mutableStateOf(false) }
    var rackTime by remember { mutableStateOf("0:00 (h:mm)") }
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.rack_time)
        )
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        // puller speed
        EocSettingTextField(
            initialValue = pullerSpeed,
            validationAction = { newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                pullerSpeed = newValue
                isErrorPS = hasError
            },
            labelString = stringResource(R.string.puller_speed),
            placeholderString = stringResource(R.string.meters_per_minute),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Next,
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // profile length
        EocSettingTextFieldWithFraction(
            initialValue = currentLength,
            validationAction = { newValue -> newValue.toIntOrNull() == null },
            onSettingChange = { newValue, hasError ->
                currentLength = newValue
                isErrorCL = hasError
            },
            labelString = stringResource(R.string.current_length),
            placeholderString = stringResource(R.string.inches),
            errorString = stringResource(R.string.whole_number_only),
            keyboardAction = ImeAction.Next,
            onFractionChange = { newFraction -> currentFraction = newFraction }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // pieces per rack
        EocSettingTextField(
            initialValue = piecesPerRack,
            validationAction = { newValue -> newValue.toIntOrNull() == null },
            onSettingChange = { newValue, hasError ->
                piecesPerRack = newValue
                isErrorPPR = hasError
            },
            labelString = stringResource(R.string.pieces_per_rack),
            placeholderString = stringResource(R.string.number),
            errorString = stringResource(R.string.whole_number_only),
            keyboardAction = ImeAction.Done,
            onDoneAction = {
                rackTime = calculateRackTime(
                    pullerSpeed.toDouble(),
                    currentLength.toDouble(),
                    DataSource.fractionMap[currentFraction] ?: 0.0,
                    piecesPerRack.toDouble()
                )
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // calculate button
        Button(
            onClick = {
                rackTime = calculateRackTime(
                    pullerSpeed.toDouble(),
                    currentLength.toDouble(),
                    DataSource.fractionMap[currentFraction] ?: 0.0,
                    piecesPerRack.toDouble()
                )
            },
            enabled = !isErrorPS && !isErrorCL && !isErrorPPR,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // rack time
        Text(
            "Rack time: $rackTime",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@SuppressLint("DefaultLocale")
fun calculateRackTime(
    pullerSpeed: Double,
    profileLength: Double,
    profileFraction: Double,
    piecesPerRack: Double
): String {
    val minutesPerRack = (piecesPerRack * (profileLength + profileFraction) * .0254 / pullerSpeed).minutes
    return minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:" + String.format("%02d", minutes) + " (h:mm)"
    }
}

@Preview(showBackground = true)
@Composable
fun RackTimeScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        RackTimeScreen(
            onBack = {},
            modifier = Modifier
        )
    }
}