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
import kotlin.time.Duration.Companion.minutes

@Serializable
object RackTimeScreen

@Composable
fun RackTimeScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pullerSpeed by remember { mutableStateOf("2.5") }
    var isErrorPS by remember { mutableStateOf(false) }
    var currentLength by remember { mutableStateOf("252") }
    var isErrorCL by remember { mutableStateOf(false) }
    var currentFraction: String by remember { mutableStateOf("0") }
    var piecesPerRack by remember { mutableStateOf("60") }
    var isErrorPPR by remember { mutableStateOf(false) }
    var rackTime by remember { mutableStateOf("0") }
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
                stringResource(R.string.rack_time),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        // puller speed
        TextField(
            singleLine = true,
            value = pullerSpeed,
            onValueChange = {
                pullerSpeed = it
                isErrorPS = pullerSpeed.toDoubleOrNull() == null
            },
            label = { Text(stringResource(R.string.puller_speed)) },
            placeholder = { Text(stringResource(R.string.meters_per_minute)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            supportingText = {
                if (isErrorPS) {
                    Text(
                        text = stringResource(R.string.decimal_number_only),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (isErrorPS) {
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
        // profile length
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
        // pieces per rack
        val kbController = LocalSoftwareKeyboardController.current
        TextField(
            singleLine = true,
            value = piecesPerRack,
            onValueChange = {
                piecesPerRack = it
                isErrorPPR = piecesPerRack.toIntOrNull() == null
            },
            label = { Text(stringResource(R.string.pieces_per_rack)) },
            placeholder = { Text(stringResource(R.string.number)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            supportingText = {
                if (isErrorPPR) {
                    Text(
                        text = stringResource(R.string.whole_number_only),
                        color = Color.Red
                    )
                }
            },
            trailingIcon = {
                if (isErrorPPR) {
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
                    rackTime = calculateRackTime(
                        pullerSpeed.toDouble(),
                        currentLength.toDouble(),
                        DataSource.fractionMap[currentFraction] ?: 0.0,
                        piecesPerRack.toDouble()
                    )
                }
            )
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // calculate button
        Button(
            onClick = {
                rackTime = calculateRackTime(
                    pullerSpeed.toDouble(),
                    currentLength.toDouble(),
                    DataSource.fractionMap[currentFraction] ?: 0.0,
                    piecesPerRack.toDouble()
                )
            },
            enabled = !isErrorPS && !isErrorCL && !isErrorPPR,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.calculate))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // rack time
        Text(
            "Rack time: $rackTime",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}

fun calculateRackTime(
    pullerSpeed: Double,
    profileLength: Double,
    profileFraction: Double,
    piecesPerRack: Double
): String {
    val minutesPerRack = (piecesPerRack * (profileLength + profileFraction) * .0254 / pullerSpeed).minutes
    return minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:$minutes (h:mm)"
    }
}

@Preview(showBackground = true)
@Composable
fun RackTimeScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        RackTimeScreen(
            onBack = {},
            modifier = Modifier
        )
    }
}