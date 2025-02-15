package com.awful.extrusionoperatorcalculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
            Spacer(modifier = Modifier.padding(24.dp))
            Text(
                stringResource(R.string.speed_change),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        // current puller speed
        TextField(
            singleLine = true,
            value = currentPullerSpeed,
            onValueChange = {
                currentPullerSpeed = it
                isErrorCPS = currentPullerSpeed.toDoubleOrNull() == null
            },
            label = { Text(stringResource(R.string.current_puller_speed)) },
            placeholder = { Text(stringResource(R.string.meters_per_minute)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (isErrorCPS) {
                    Text(
                        text = stringResource(R.string.decimal_number_only),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (isErrorCPS) {
                    Icon(
                        Icons.Filled.Warning,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // current feeder speed
        TextField(
            singleLine = true,
            value = currentFeederSpeed,
            onValueChange = {
                currentFeederSpeed = it
                isErrorCFS = currentFeederSpeed.toDoubleOrNull() == null
            },
            label = { Text(stringResource(R.string.current_feeder_speed)) },
            placeholder = { Text(stringResource(R.string.rpm)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (isErrorCFS) {
                    Text(
                        text = stringResource(R.string.decimal_number_only),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (isErrorCFS) {
                    Icon(
                        Icons.Filled.Warning,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // current extruder speed
        TextField(
            singleLine = true,
            value = currentExtruderSpeed,
            onValueChange = {
                currentExtruderSpeed = it
                isErrorCES = currentExtruderSpeed.toDoubleOrNull() == null
            },
            label = { Text(stringResource(R.string.current_extruder_speed)) },
            placeholder = { Text(stringResource(R.string.rpm)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (isErrorCES) {
                    Text(
                        text = stringResource(R.string.decimal_number_only),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (isErrorCES) {
                    Icon(
                        Icons.Filled.Warning,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // target puller speed
        val kbController = LocalSoftwareKeyboardController.current
        TextField(
            singleLine = true,
            value = targetPullerSpeed,
            onValueChange = {
                targetPullerSpeed = it
                isErrorTPS = targetPullerSpeed.toDoubleOrNull() == null
            },
            label = { Text(stringResource(R.string.target_puller_speed)) },
            placeholder = { Text(stringResource(R.string.meters_per_minute)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            supportingText = {
                if (isErrorTPS) {
                    Text(
                        text = stringResource(R.string.decimal_number_only),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (isErrorTPS) {
                    Icon(
                        Icons.Filled.Warning,
                        stringResource(R.string.error),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    kbController?.hide()
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