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
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

@Serializable
object WeatherstripTimeScreen

@Composable
fun WeatherstripTimeScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pullerSpeed by remember { mutableStateOf("2.5") }
    var isErrorPS by remember { mutableStateOf(false) }
    var spoolLength by remember { mutableStateOf("3500") }
    var isErrorSL by remember { mutableStateOf(false) }
    var spoolTime by remember { mutableStateOf("0:00 (h:mm)") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.weatherstrip_calculation)
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
            keyboardAction = ImeAction.Next
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // spool length
        EocSettingTextField(
            initialValue = spoolLength,
            validationAction = { newValue -> newValue.toIntOrNull() == null },
            onSettingChange = { newValue, hasError ->
                spoolLength = newValue
                isErrorSL = hasError
            },
            labelString = stringResource(R.string.spool_length),
            placeholderString = stringResource(R.string.feet),
            errorString = stringResource(R.string.whole_number_only),
            keyboardAction = ImeAction.Done,
            onDoneAction = {
                spoolTime = calculateSpoolTime(
                    pullerSpeed.toDouble(),
                    spoolLength.toInt()
                )
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // calculate button
        Button(
            onClick = {
                spoolTime = calculateSpoolTime(
                    pullerSpeed.toDouble(),
                    spoolLength.toInt()
                )
            },
            enabled = !isErrorPS && !isErrorSL,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // rack time
        Text(
            "Rack time: $spoolTime",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@SuppressLint("DefaultLocale")
fun calculateSpoolTime(
    pullerSpeed: Double,
    spoolLength: Int
): String {
    val minutesPerRack = (spoolLength * .3048 / pullerSpeed).minutes
    return minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:" + String.format("%02d", minutes) + " (h:mm)"
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherstripTimeScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        WeatherstripTimeScreen(
            onBack = {},
            modifier = Modifier
        )
    }
}