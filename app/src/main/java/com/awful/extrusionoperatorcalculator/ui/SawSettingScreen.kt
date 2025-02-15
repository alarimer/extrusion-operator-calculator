package com.awful.extrusionoperatorcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.awful.extrusionoperatorcalculator.data.DataSource
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable
import kotlin.math.round

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
                stringResource(R.string.saw_setting),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        // current length
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                singleLine = true,
                value = currentLength,
                onValueChange = {
                    currentLength = it
                    isErrorCL = currentLength.toIntOrNull() == null
                },
                label = { Text(stringResource(R.string.current_length)) },
                placeholder = { Text(stringResource(R.string.inches)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (isErrorCL) {
                        Text(
                            text = stringResource(R.string.whole_number_only),
                            color = Color.Red
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorCL) {
                        Icon(
                            Icons.Filled.Warning,
                            stringResource(R.string.error),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Text(currentFraction, modifier = Modifier.padding(16.dp))
            Box(
                modifier = modifier
            ) {
                var isExpanded by remember { mutableStateOf(false) }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.select_fraction)
                    )
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DataSource.fractionMap.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.key) },
                            onClick = {
                                currentFraction = option.key
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // desired length
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                singleLine = true,
                value = desiredLength,
                onValueChange = {
                    desiredLength = it
                    isErrorDL = desiredLength.toIntOrNull() == null
                },
                label = { Text(stringResource(R.string.desired_length)) },
                placeholder = { Text(stringResource(R.string.inches)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (isErrorDL) {
                        Text(
                            text = stringResource(R.string.whole_number_only),
                            color = Color.Red
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorDL) {
                        Icon(
                            Icons.Filled.Warning,
                            stringResource(R.string.error),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            Text(desiredFraction, modifier = Modifier.padding(16.dp))
            Box(
                modifier = modifier
            ) {
                var isExpanded by remember { mutableStateOf(false) }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.select_fraction))
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DataSource.fractionMap.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.key) },
                            onClick = {
                                desiredFraction = option.key
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // current setting
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val kbController = LocalSoftwareKeyboardController.current
            TextField(
                singleLine = true,
                value = currentSetting,
                onValueChange = {
                    currentSetting = it
                    isErrorCS = currentSetting.toDoubleOrNull() == null
                },
                label = { Text(stringResource(R.string.current_setting)) },
                placeholder = { Text(stringResource(R.string.inches)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (isErrorCS) {
                        Text(
                            text = stringResource(R.string.decimal_number_only),
                            color = Color.Red
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorCS) {
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
                        newSetting = calculateNewSetting(
                            currentLength.toDouble(),
                            DataSource.fractionMap[currentFraction] ?: 0.0,
                            desiredLength.toDouble(),
                            DataSource.fractionMap[desiredFraction] ?: 0.0,
                            currentSetting.toDouble()
                        )
                    }
                )
            )
        }
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

// rounds the result to the nearest 1/8 inch
fun calculateNewSetting(
    currentLength: Double,
    currentFraction: Double,
    desiredLength: Double,
    desiredFraction:Double,
    currentSetting: Double
): String {
    return (round((currentSetting + currentFraction) * (desiredLength + desiredFraction) / currentLength * 8) / 8).toString()
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