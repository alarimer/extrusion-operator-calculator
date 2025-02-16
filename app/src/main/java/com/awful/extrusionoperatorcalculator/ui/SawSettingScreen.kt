package com.awful.extrusionoperatorcalculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Serializable
object SawSettingScreen

@Composable
fun SawSettingScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentLength by remember { mutableStateOf("252") }
    var isErrorCL by remember { mutableStateOf(false) }
    var currentFraction: String by remember { mutableStateOf("0") }
    var desiredLength by remember { mutableStateOf("252") }
    var isErrorDL by remember { mutableStateOf(false) }
    var desiredFraction: String by remember { mutableStateOf("0") }
    var currentSetting by remember { mutableStateOf("252") }
    var isErrorCS by remember { mutableStateOf(false) }
    var newSetting by remember { mutableStateOf("0") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.saw_setting)
        )
        // current length
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
            modifier = modifier,
            onFractionChange = { newFraction -> currentFraction = newFraction }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // desired length
        EocSettingTextFieldWithFraction(
            initialValue = desiredLength,
            validationAction = { newValue -> newValue.toIntOrNull() == null },
            onSettingChange = { newValue, hasError ->
                desiredLength = newValue
                isErrorDL = hasError
            },
            labelString = stringResource(R.string.desired_length),
            placeholderString = stringResource(R.string.inches),
            errorString = stringResource(R.string.whole_number_only),
            keyboardAction = ImeAction.Next,
            modifier = modifier,
            onFractionChange = { newFraction -> desiredFraction = newFraction }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // current setting
        EocSettingTextField(
            initialValue = currentSetting,
            validationAction = { newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                currentSetting = newValue
                isErrorCS = hasError
            },
            labelString = stringResource(R.string.current_setting),
            placeholderString = stringResource(R.string.inches),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Done,
            onDoneAction = {
                newSetting = calculateNewSetting(
                    currentLength.toDouble(),
                    DataSource.fractionMap[currentFraction] ?: 0.0,
                    desiredLength.toDouble(),
                    DataSource.fractionMap[desiredFraction] ?: 0.0,
                    currentSetting.toDouble()
                )
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // calculate button
        Button(
            onClick = {
                newSetting = calculateNewSetting(
                    currentLength.toDouble(),
                    DataSource.fractionMap[currentFraction] ?: 0.0,
                    desiredLength.toDouble(),
                    DataSource.fractionMap[desiredFraction] ?: 0.0,
                    currentSetting.toDouble()
                )
            },
            enabled = !isErrorCL && !isErrorDL && !isErrorCS,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // new setting
        Text(
            "New Setting: $newSetting",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@SuppressLint("DefaultLocale")
fun calculateNewSetting(
    currentLength: Double,
    currentFraction: Double,
    desiredLength: Double,
    desiredFraction:Double,
    currentSetting: Double
): String {
    return String.format(
        "%.3f",
        currentSetting * (desiredLength + desiredFraction) / (currentLength + currentFraction)
    )
}

@Preview(showBackground = true)
@Composable
fun SawSettingScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        SawSettingScreen(
            onBack = {},
            modifier = Modifier
        )
    }
}