package com.awful.extrusionoperatorcalculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    isWideDisplay:Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pullerSpeed by remember { mutableStateOf("2.5") }
    var isErrorPS by remember { mutableStateOf(false) }
    var spoolLength by remember { mutableStateOf("3500") }
    var isErrorSL by remember { mutableStateOf(false) }
    var spoolTime by remember { mutableStateOf("0:00 (h:mm)") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = { localFocusManager.clearFocus() })
            },
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.weatherstrip_calculation)
        )
        if (isWideDisplay) {
            Row(
                modifier =  modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
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
                            keyboardController?.hide()
                            spoolTime = calculateSpoolTime(
                                pullerSpeed.toDouble(),
                                spoolLength.toInt()
                            )
                        }
                    )
                }
                Spacer(
                    modifier = Modifier.padding(16.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    // calculate button
                    Button(
                        onClick = {
                            keyboardController?.hide()
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
                        "Spool time: $spoolTime",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {
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
                    keyboardController?.hide()
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
                    keyboardController?.hide()
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
                "Spool time: $spoolTime",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
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

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp",
    name = "portrait",
    showSystemUi = true
)
@Composable
fun WeatherstripTimeScreenPreviewPortrait() {
    ExtrusionOperatorCalculatorTheme {
        WeatherstripTimeScreen(
            isWideDisplay = false,
            onBack = {},
            modifier = Modifier
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    name = "landscape",
    showSystemUi = true
)
@Composable
fun WeatherstripTimeScreenPreviewLandscape() {
    ExtrusionOperatorCalculatorTheme {
        WeatherstripTimeScreen(
            isWideDisplay = true,
            onBack = {},
            modifier = Modifier
        )
    }
}