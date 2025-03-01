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
object SpeedChangeScreen

@Composable
fun SpeedChangeScreen(
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
            titleText = stringResource(R.string.speed_change)
        )
        if (isWideDisplay) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    // current puller speed
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentPullerSpeed,
                        onValueChange = { newValue -> eocViewModel.setCPS(newValue) },
                        isError = { eocViewModel.isErrorCPS },
                        labelString = stringResource(R.string.current_puller_speed),
                        placeholderString = stringResource(R.string.meters_per_minute),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Next
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // current feeder speed
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentFeederSpeed,
                        onValueChange = { newValue -> eocViewModel.setCFS(newValue) },
                        isError = { eocViewModel.isErrorCFS },
                        labelString = stringResource(R.string.current_feeder_speed),
                        placeholderString = stringResource(R.string.rpm),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Next
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // current extruder speed
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentExtruderSpeed,
                        onValueChange = { newValue -> eocViewModel.setCES(newValue) },
                        isError = { eocViewModel.isErrorCES },
                        labelString = stringResource(R.string.current_extruder_speed),
                        placeholderString = stringResource(R.string.rpm),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Next
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // target puller speed
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.targetPullerSpeed,
                        onValueChange = { newValue -> eocViewModel.setTPS(newValue) },
                        isError = { eocViewModel.isErrorTPS },
                        labelString = stringResource(R.string.target_puller_speed),
                        placeholderString = stringResource(R.string.meters_per_minute),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Done,
                        onDoneAction = {
                            keyboardController?.hide()
                            eocViewModel.calculateSpeedSettings()
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
                            eocViewModel.calculateSpeedSettings()
                        },
                        enabled = !eocViewModel.isErrorCPS && !eocViewModel.isErrorCFS && !eocViewModel.isErrorCES && !eocViewModel.isErrorTPS,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.calculate))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // new settings
                    Text(
                        text = String.format(
                            stringResource(R.string.new_feeder_setting_label),
                            eocViewModel.newFeederSetting
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = String.format(
                            stringResource(R.string.new_extruder_setting_label),
                            eocViewModel.newExtruderSetting
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {    // portrait
            // current puller speed
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentPullerSpeed,
                onValueChange = { newValue -> eocViewModel.setCPS(newValue) },
                isError = { eocViewModel.isErrorCPS },
                labelString = stringResource(R.string.current_puller_speed),
                placeholderString = stringResource(R.string.meters_per_minute),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Next
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // current feeder speed
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentFeederSpeed,
                onValueChange = { newValue -> eocViewModel.setCFS(newValue) },
                isError = { eocViewModel.isErrorCFS },
                labelString = stringResource(R.string.current_feeder_speed),
                placeholderString = stringResource(R.string.rpm),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Next
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // current extruder speed
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentExtruderSpeed,
                onValueChange = { newValue -> eocViewModel.setCES(newValue) },
                isError = { eocViewModel.isErrorCES },
                labelString = stringResource(R.string.current_extruder_speed),
                placeholderString = stringResource(R.string.rpm),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Next
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // target puller speed
            EocSettingTextFieldVM(
                initialValue = eocViewModel.targetPullerSpeed,
                onValueChange = { newValue -> eocViewModel.setTPS(newValue) },
                isError = { eocViewModel.isErrorTPS },
                labelString = stringResource(R.string.target_puller_speed),
                placeholderString = stringResource(R.string.meters_per_minute),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Done,
                onDoneAction = {
                    keyboardController?.hide()
                    eocViewModel.calculateSpeedSettings()
                }
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // calculate button
            Button(
                onClick = {
                    keyboardController?.hide()
                    eocViewModel.calculateSpeedSettings()
                },
                enabled = !eocViewModel.isErrorCPS && !eocViewModel.isErrorCFS && !eocViewModel.isErrorCES && !eocViewModel.isErrorTPS,
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.calculate))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // new settings
            Text(
                text = String.format(
                    stringResource(R.string.new_feeder_setting_label),
                    eocViewModel.newFeederSetting
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = String.format(
                    stringResource(R.string.new_extruder_setting_label),
                    eocViewModel.newExtruderSetting
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
        }   // orientation
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp",
    name = "portrait",
    showSystemUi = true
)
@Composable
fun SpeedChangeSettingScreenPreviewPortrait() {
    ExtrusionOperatorCalculatorTheme {
        SpeedChangeScreen(
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
fun SpeedChangeSettingScreenPreviewLandscape() {
    ExtrusionOperatorCalculatorTheme {
        SpeedChangeScreen(
            isWideDisplay = true,
            onBack = {},
            modifier = Modifier
        )
    }
}