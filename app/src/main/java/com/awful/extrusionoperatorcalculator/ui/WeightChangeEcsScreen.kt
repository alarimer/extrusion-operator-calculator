package com.awful.extrusionoperatorcalculator.ui

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.awful.extrusionoperatorcalculator.R
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable

@Serializable
object WeightChangeEcsScreen

@Composable
fun WeightChangeEcsScreen(
    modifier: Modifier = Modifier,
    eocViewModel: EocViewModel = viewModel(),
    isWideDisplay: Boolean,
    onBack: () -> Unit
) {
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
            titleText = stringResource(R.string.weight_change_ecs)
        )
        if (isWideDisplay) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    // current set point
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentSetPoint,
                        onValueChange = { newValue -> eocViewModel.setCSP(newValue) },
                        isError = { eocViewModel.isErrorCSP },
                        labelString = stringResource(R.string.current_set_point),
                        placeholderString = stringResource(R.string.grams_per_meter),
                        errorString = stringResource(R.string.whole_number_only),
                        keyboardAction = ImeAction.Next
                    )
                    // current weight
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentWeight,
                        onValueChange = { newValue -> eocViewModel.setCW(newValue) },
                        isError = { eocViewModel.isErrorCW },
                        labelString = stringResource(R.string.current_weight),
                        placeholderString = stringResource(R.string.grams_per_meter),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Done,
                        onDoneAction = {
                            keyboardController?.hide()
                            eocViewModel.calculateWeightChangeBySetChange()
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
                            eocViewModel.calculateWeightChangeBySetChange()
                        },
                        enabled = !eocViewModel.isErrorCSP && !eocViewModel.isErrorCW,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.calculate))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // weight change by set point change
                    Text(
                        text = String.format(
                            stringResource(R.string.weight_change_by_set_change),
                            eocViewModel.weightChangeBySetChange
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {
            // current set point
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentSetPoint,
                onValueChange = { newValue -> eocViewModel.setCSP(newValue) },
                isError = { eocViewModel.isErrorCSP },
                labelString = stringResource(R.string.current_set_point),
                placeholderString = stringResource(R.string.grams_per_meter),
                errorString = stringResource(R.string.whole_number_only),
                keyboardAction = ImeAction.Next
            )
            // current weight
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentWeight,
                onValueChange = { newValue -> eocViewModel.setCW(newValue) },
                isError = { eocViewModel.isErrorCW },
                labelString = stringResource(R.string.current_weight),
                placeholderString = stringResource(R.string.grams_per_meter),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Done,
                onDoneAction = {
                    keyboardController?.hide()
                    eocViewModel.calculateWeightInfo()
                }
            )
            Spacer(
                modifier = Modifier.padding(16.dp)
            )
            // calculate button
            Button(
                onClick = {
                    keyboardController?.hide()
                    eocViewModel.calculateWeightChangeBySetChange()
                },
                enabled = !eocViewModel.isErrorCSP && !eocViewModel.isErrorCW,
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.calculate))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // weight change by set point change
            Text(
                text = String.format(
                    stringResource(R.string.weight_change_by_set_change),
                    eocViewModel.weightChangeBySetChange
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp",
    name = "portrait",
    showSystemUi = true
)
@Composable
fun WeightChangeEcsScreenPortrait() {
    ExtrusionOperatorCalculatorTheme {
        WeightChangeEcsScreen(
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
fun WeightChangeEcsScreenLandscape() {
    ExtrusionOperatorCalculatorTheme {
        WeightChangeEcsScreen(
            isWideDisplay = true,
            onBack = {},
            modifier = Modifier
        )
    }
}