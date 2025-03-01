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
object RackTimeScreen

@Composable
fun RackTimeScreen(
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
            titleText = stringResource(R.string.rack_time)
        )
        if (isWideDisplay) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    // puller speed
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.currentPullerSpeed,
                        onValueChange = { newValue -> eocViewModel.setCPS(newValue) },
                        isError = { eocViewModel.isErrorCPS },
                        labelString = stringResource(R.string.puller_speed),
                        placeholderString = stringResource(R.string.meters_per_minute),
                        errorString = stringResource(R.string.decimal_number_only),
                        keyboardAction = ImeAction.Next,
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // profile length
                    EocSettingTextFieldWithFractionVM(
                        initialValue = eocViewModel.currentLength,
                        onValueChange = { newValue -> eocViewModel.setCL(newValue) },
                        isError = { eocViewModel.isErrorCL },
                        labelString = stringResource(R.string.current_length),
                        placeholderString = stringResource(R.string.inches),
                        errorString = stringResource(R.string.whole_number_only),
                        keyboardAction = ImeAction.Next,
                        initialFraction = eocViewModel.currentFraction,
                        onFractionChange = { newFraction -> eocViewModel.setCF(newFraction) }
                    )
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // pieces per rack
                    EocSettingTextFieldVM(
                        initialValue = eocViewModel.piecesPerRack,
                        onValueChange = { newValue -> eocViewModel.setPPR(newValue) },
                        isError = { eocViewModel.isErrorPPR },
                        labelString = stringResource(R.string.pieces_per_rack),
                        placeholderString = stringResource(R.string.number),
                        errorString = stringResource(R.string.whole_number_only),
                        keyboardAction = ImeAction.Done,
                        onDoneAction = {
                            keyboardController?.hide()
                            eocViewModel.calculateRackTime()
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
                            eocViewModel.calculateRackTime()
                        },
                        enabled = !eocViewModel.isErrorCPS && !eocViewModel.isErrorCL && !eocViewModel.isErrorPPR,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.calculate))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    // rack time
                    Text(
                        text = eocViewModel.rackTime,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {
            Spacer(
                modifier = Modifier.padding(16.dp)
            )
            // puller speed
            EocSettingTextFieldVM(
                initialValue = eocViewModel.currentPullerSpeed,
                onValueChange = { newValue -> eocViewModel.setCPS(newValue) },
                isError = { eocViewModel.isErrorCPS },
                labelString = stringResource(R.string.puller_speed),
                placeholderString = stringResource(R.string.meters_per_minute),
                errorString = stringResource(R.string.decimal_number_only),
                keyboardAction = ImeAction.Next,
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // profile length
            EocSettingTextFieldWithFractionVM(
                initialValue = eocViewModel.currentLength,
                onValueChange = { newValue -> eocViewModel.setCL(newValue) },
                isError = { eocViewModel.isErrorCL },
                labelString = stringResource(R.string.current_length),
                placeholderString = stringResource(R.string.inches),
                errorString = stringResource(R.string.whole_number_only),
                keyboardAction = ImeAction.Next,
                initialFraction = eocViewModel.currentFraction,
                onFractionChange = { newFraction -> eocViewModel.setCF(newFraction) }
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // pieces per rack
            EocSettingTextFieldVM(
                initialValue = eocViewModel.piecesPerRack,
                onValueChange = { newValue -> eocViewModel.setPPR(newValue) },
                isError = { eocViewModel.isErrorPPR },
                labelString = stringResource(R.string.pieces_per_rack),
                placeholderString = stringResource(R.string.number),
                errorString = stringResource(R.string.whole_number_only),
                keyboardAction = ImeAction.Done,
                onDoneAction = {
                    keyboardController?.hide()
                    eocViewModel.calculateRackTime()
                }
            )
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // calculate button
            Button(
                onClick = {
                    keyboardController?.hide()
                    eocViewModel.calculateRackTime()
                },
                enabled = !eocViewModel.isErrorCPS && !eocViewModel.isErrorCL && !eocViewModel.isErrorPPR,
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.calculate))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            // rack time
            Text(
                text = eocViewModel.rackTime,
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
fun RackTimeScreenPreviewPortrait() {
    ExtrusionOperatorCalculatorTheme {
        RackTimeScreen(
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
fun RackTimeScreenPreviewLandscape() {
    ExtrusionOperatorCalculatorTheme {
        RackTimeScreen(
            isWideDisplay = true,
            onBack = {},
            modifier = Modifier
        )
    }
}