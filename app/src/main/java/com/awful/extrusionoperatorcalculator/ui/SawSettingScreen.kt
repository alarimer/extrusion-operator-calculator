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
object SawSettingScreen

@Composable
fun SawSettingScreen(
    modifier: Modifier = Modifier,
    eocViewModel: EocViewModel = viewModel(),
    isWideDisplay: Boolean,
    onBack: () -> Unit,
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
            titleText = stringResource(R.string.saw_setting)
        )
        if (isWideDisplay)
        {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    // current length
                    EocSettingTextFieldWithFractionVM(
                        initialValue = eocViewModel.currentLength,
                        onValueChange = { newValue -> eocViewModel.setCL(newValue) },
                        isError = { eocViewModel.isErrorCL },
                        labelString = stringResource(R.string.current_length),
                        placeholderString = stringResource(R.string.inches),
                        errorString = stringResource(R.string.whole_number_only),
                        keyboardAction = ImeAction.Next,
                        onFractionChange = { newFraction -> eocViewModel.setCF(newFraction) }
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // desired length
                    EocSettingTextFieldWithFractionVM(
                        initialValue = eocViewModel.desiredLength,
                        onValueChange = { newValue -> eocViewModel.setDL(newValue) },
                        isError = { eocViewModel.isErrorDL },
                        labelString = stringResource(R.string.desired_length),
                        placeholderString = stringResource(R.string.inches),
                        errorString = stringResource(R.string.whole_number_only),
                        keyboardAction = ImeAction.Next,
                        initialFraction = eocViewModel.desiredFraction,
                        onFractionChange = { newFraction -> eocViewModel.setDF(newFraction) }
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // current setting
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentSetting,
                        onValueChange = { newValue -> eocViewModel.setCS(newValue) },
                        isError = { eocViewModel.isErrorCS },
                        labelString = stringResource(R.string.current_setting),
                        placeholderString = stringResource(R.string.inches),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Done,
                        onDoneAction = {
                            keyboardController?.hide()
                            eocViewModel.calculateSawSetting()
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
                            eocViewModel.calculateSawSetting()
                        },
                        enabled = !eocViewModel.isErrorCL && !eocViewModel.isErrorDL && !eocViewModel.isErrorCS,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.calculate))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // new setting
                    Text(
                        text = String.format(stringResource(R.string.new_setting_label), eocViewModel.sawSetting),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {
            // current length
            EocSettingTextFieldWithFractionVM(
                initialValue = eocViewModel.currentLength,
                onValueChange = { newValue -> eocViewModel.setCL(newValue) },
                isError = { eocViewModel.isErrorCL },
                labelString = stringResource(R.string.current_length),
                placeholderString = stringResource(R.string.inches),
                errorString = stringResource(R.string.whole_number_only),
                keyboardAction = ImeAction.Next,
                onFractionChange = { newFraction -> eocViewModel.setCF(newFraction) }
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // desired length
            EocSettingTextFieldWithFractionVM(
                initialValue = eocViewModel.desiredLength,
                onValueChange = { newValue -> eocViewModel.setDL(newValue) },
                isError = { eocViewModel.isErrorDL },
                labelString = stringResource(R.string.desired_length),
                placeholderString = stringResource(R.string.inches),
                errorString = stringResource(R.string.whole_number_only),
                keyboardAction = ImeAction.Next,
                initialFraction = eocViewModel.desiredFraction,
                onFractionChange = { newFraction -> eocViewModel.setDF(newFraction) }
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // current setting
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentSetting,
                onValueChange = { newValue -> eocViewModel.setCS(newValue) },
                isError = { eocViewModel.isErrorCS },
                labelString = stringResource(R.string.current_setting),
                placeholderString = stringResource(R.string.inches),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Done,
                onDoneAction = {
                    keyboardController?.hide()
                    eocViewModel.calculateSawSetting()
                }
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // calculate button
            Button(
                onClick = {
                    keyboardController?.hide()
                    eocViewModel.calculateSawSetting()
                },
                enabled = !eocViewModel.isErrorCL && !eocViewModel.isErrorDL && !eocViewModel.isErrorCS,
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.calculate))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // new setting
            Text(
                text = String.format(stringResource(R.string.new_setting_label), eocViewModel.sawSetting),
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
fun SawSettingScreenPreviewPortrait() {
    ExtrusionOperatorCalculatorTheme {
        SawSettingScreen(
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
fun SawSettingScreenPreviewLandscape() {
    ExtrusionOperatorCalculatorTheme {
        SawSettingScreen(
            isWideDisplay = true,
            onBack = {},
            modifier = Modifier
        )
    }
}