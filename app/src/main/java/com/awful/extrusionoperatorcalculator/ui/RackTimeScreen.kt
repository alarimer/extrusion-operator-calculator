package com.awful.extrusionoperatorcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
    modifier: Modifier = Modifier
) {
    var pullerSpeed by remember { mutableStateOf("2.5") }
    var currentLength by remember { mutableStateOf("252") }
    var currentFraction: String by remember { mutableStateOf("0") }
    var piecesPerRack by remember { mutableStateOf("60") }
    var rackTime by remember { mutableStateOf("0") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(R.string.rack_time),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        // puller speed
        TextField(
            value = pullerSpeed,
            onValueChange = { pullerSpeed = it },
            label = { Text(stringResource(R.string.puller_speed)) },
            placeholder = { Text(stringResource(R.string.meters_per_minute)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // profile length
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = currentLength,
                onValueChange = { currentLength = it },
                label = { Text(stringResource(R.string.current_length)) },
                placeholder = { Text(stringResource(R.string.inches)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Text(currentFraction, modifier = Modifier.padding(16.dp))
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
                                currentFraction = option.key
                                isExpanded = false
                                currentLength = (currentLength.toDouble().toInt() + option.value).toString()
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
        TextField(
            value = piecesPerRack,
            onValueChange = { piecesPerRack = it },
            label = { Text(stringResource(R.string.pieces_per_rack)) },
            placeholder = { Text(stringResource(R.string.number)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
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
                    piecesPerRack.toDouble()
                )
            },
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
    piecesPerRack: Double
): String {
    val minutesPerRack = (piecesPerRack * profileLength * .0254 / pullerSpeed).minutes
    return minutesPerRack.toComponents {
            hours, minutes, _, _ -> "$hours:$minutes (h:mm)"
    }
}

@Preview(showBackground = true)
@Composable
fun RackTimeScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        RackTimeScreen(
            modifier = Modifier
        )
    }
}