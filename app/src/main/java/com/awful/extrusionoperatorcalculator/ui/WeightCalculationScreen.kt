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
object WeightCalculationScreen

@Composable
fun WeightCalculationScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentWeight by remember { mutableStateOf("950.0") }
    var isErrorCW by remember { mutableStateOf(false) }
    var standardWeight by remember { mutableStateOf("1000") }
    var isErrorSW by remember { mutableStateOf(false) }
    var percentWeight by remember { mutableStateOf("95.0") }
    var minimumWeight by remember { mutableStateOf("900.0") }
    var maximumWeight by remember { mutableStateOf("1100.0") }
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
                stringResource(R.string.weight_calculation),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        // current weight
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                singleLine = true,
                value = currentWeight,
                onValueChange = {
                    currentWeight = it
                    isErrorCW = currentWeight.toDoubleOrNull() == null
                },
                label = { Text(stringResource(R.string.current_weight)) },
                placeholder = { Text(stringResource(R.string.grams_per_meter)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (isErrorCW) {
                        Text(
                            text = stringResource(R.string.decimal_number_only),
                            color = Color.Red
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorCW) {
                        Icon(
                            Icons.Filled.Warning,
                            stringResource(R.string.error),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
        // standard weight
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val kbController = LocalSoftwareKeyboardController.current
            TextField(
                singleLine = true,
                value = standardWeight,
                onValueChange = {
                    standardWeight = it
                    isErrorSW = standardWeight.toDoubleOrNull() == null
                },
                label = { Text(stringResource(R.string.standard_weight)) },
                placeholder = { Text(stringResource(R.string.grams_per_meter)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    if (isErrorSW) {
                        Text(
                            text = stringResource(R.string.decimal_number_only),
                            color = Color.Red
                        )
                    }
                },
                trailingIcon = {
                    if (isErrorSW) {
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
                        val (pctWt, minWt, maxWt) = calculateWeightInfo(
                            currentWeight.toDouble(),
                            standardWeight.toDouble()
                        )
                        percentWeight = pctWt
                        minimumWeight = minWt
                        maximumWeight = maxWt
                    }
                )
            )
        }
        // calculate button
        Button(
            onClick = {
                val (pctWt, minWt, maxWt) = calculateWeightInfo(
                    currentWeight.toDouble(),
                    standardWeight.toDouble()
                )
                percentWeight = pctWt
                minimumWeight = minWt
                maximumWeight = maxWt
            },
            enabled = !isErrorCW && !isErrorSW,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // percentage, minimum, and maximum
        Text(
            "Percent Weight: $percentWeight%",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            "Minimum Weight: $minimumWeight g/m",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            "Maximum Weight: $maximumWeight g/m",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@SuppressLint("DefaultLocale")
fun calculateWeightInfo(
    currentWeight: Double,
    standardWeight: Double
): Triple<String, String, String> {
    return Triple(
        String.format("%.2f", currentWeight / standardWeight * 100),
        String.format("%.1f", standardWeight * 0.9),
        String.format("%.1f", standardWeight * 1.1)
    )
}

@Preview(showBackground = true)
@Composable
fun WeightCalculationScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        WeightCalculationScreen(
            onBack = {},
            modifier = Modifier
        )
    }
}