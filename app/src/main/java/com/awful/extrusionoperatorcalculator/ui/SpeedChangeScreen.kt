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
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable

@Serializable
object SpeedChangeScreen

@Composable
fun SpeedChangeScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPullerSpeed by remember { mutableStateOf("2.5") }
    var isErrorCPS by remember { mutableStateOf(false) }
    var currentFeederSpeed by remember { mutableStateOf("30.5") }
    var isErrorCFS by remember { mutableStateOf(false) }
    var currentExtruderSpeed by remember { mutableStateOf("15.5") }
    var isErrorCES by remember { mutableStateOf(false) }
    var targetPullerSpeed by remember { mutableStateOf("4.5") }
    var isErrorTPS by remember { mutableStateOf(false) }
    var newFeederSetting by remember { mutableStateOf("0.0") }
    var newExtruderSetting by remember { mutableStateOf("0.0") }
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.speed_change)
        )
        // current puller speed
        EocSettingTextField(
            initialValue = currentPullerSpeed,
            validationAction = { newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                currentPullerSpeed = newValue
                isErrorCPS = hasError
            },
            labelString = stringResource(R.string.current_puller_speed),
            placeholderString = stringResource(R.string.meters_per_minute),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // current feeder speed
        EocSettingTextField(
            initialValue = currentFeederSpeed,
            validationAction = { newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                currentFeederSpeed = newValue
                isErrorCFS = hasError
            },
            labelString = stringResource(R.string.current_feeder_speed),
            placeholderString = stringResource(R.string.rpm),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // current extruder speed
        EocSettingTextField(
            initialValue = currentExtruderSpeed,
            validationAction = {  newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                currentExtruderSpeed = newValue
                isErrorCES = hasError
            },
            labelString = stringResource(R.string.current_extruder_speed),
            placeholderString = stringResource(R.string.rpm),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // target puller speed
        EocSettingTextField(
            initialValue = targetPullerSpeed,
            validationAction = {  newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                targetPullerSpeed = newValue
                isErrorTPS = hasError
            },
            labelString = stringResource(R.string.target_puller_speed),
            placeholderString = stringResource(R.string.meters_per_minute),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Done,
            onDoneAction = {
                val (nfs, nes) =
                    calculateNewSettings(
                        currentPullerSpeed.toDouble(),
                        currentFeederSpeed.toDouble(),
                        currentExtruderSpeed.toDouble(),
                        targetPullerSpeed.toDouble()
                    )
                newFeederSetting = nfs
                newExtruderSetting = nes
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // calculate button
        Button(
            onClick = {
                val (nfs, nes) =
                calculateNewSettings(
                    currentPullerSpeed.toDouble(),
                    currentFeederSpeed.toDouble(),
                    currentExtruderSpeed.toDouble(),
                    targetPullerSpeed.toDouble()
                )
                newFeederSetting = nfs
                newExtruderSetting = nes
            },
            enabled = !isErrorCPS && !isErrorCFS && !isErrorCES && !isErrorTPS,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // new settings
        Text(
            "New Feeder Setting: $newFeederSetting",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            "New Extruder Setting: $newExtruderSetting",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@SuppressLint("DefaultLocale")
fun calculateNewSettings(
    currentPullerSetting: Double,
    currentFeederSetting: Double,
    currentExtruderSetting: Double,
    targetPullerSpeed: Double
) :Pair<String, String> {
    return Pair(
        String.format(
            "%.2f",
            targetPullerSpeed * currentFeederSetting / currentPullerSetting
        ),
        String.format(
            "%.2f",
            targetPullerSpeed * currentExtruderSetting / currentPullerSetting
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SpeedChangeSettingScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        SpeedChangeScreen(
            onBack = {},
            modifier = Modifier
        )
    }
}