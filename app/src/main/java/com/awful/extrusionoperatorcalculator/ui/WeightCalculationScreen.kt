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
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.weight_calculation)
        )
        // current weight
        EocSettingTextField(
            initialValue = currentWeight,
            validationAction = { newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = { newValue, hasError ->
                currentWeight = newValue
                isErrorCW = hasError
            },
            labelString = stringResource(R.string.current_weight),
            placeholderString = stringResource(R.string.grams_per_meter),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Next
        )
        // standard weight
        EocSettingTextField(
            initialValue = standardWeight,
            validationAction = { newValue -> newValue.toDoubleOrNull() == null },
            onSettingChange = {  newValue, hasError ->
                standardWeight = newValue
                isErrorSW = hasError
            },
            labelString = stringResource(R.string.standard_weight),
            placeholderString = stringResource(R.string.grams_per_meter),
            errorString = stringResource(R.string.decimal_number_only),
            keyboardAction = ImeAction.Done,
            onDoneAction = {
                val (pctWt, minWt, maxWt) = calculateWeightInfo(
                    currentWeight.toDouble(),
                    standardWeight.toDouble()
                )
                percentWeight = pctWt
                minimumWeight = minWt
                maximumWeight = maxWt
            }
        )
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