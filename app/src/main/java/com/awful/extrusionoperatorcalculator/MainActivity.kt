package com.awful.extrusionoperatorcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExtrusionOperatorCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SawSettingScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SawSettingScreen(
    modifier: Modifier = Modifier
) {
    var currentExpanded by remember { mutableStateOf(false) }
    var desiredExpanded by remember { mutableStateOf(false) }
    val menuItemDataMap = mapOf(
        "0" to 0.0,
        "1/8" to 0.125,
        "1/4" to 0.25,
        "3/8" to 0.375,
        "1/2" to 0.5,
        "5/8" to 0.625,
        "3/4" to .75,
        "7/8" to 0.875
    )
    var currentLength by remember { mutableStateOf("") }
    var currentFraction: String by remember { mutableStateOf("0")}
    var desiredLength by remember { mutableStateOf("") }
    var desiredFraction: String by remember { mutableStateOf("0")}
    var currentSetting by remember { mutableStateOf("") }
    var desiredSetting by remember { mutableStateOf("0") }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        // current length
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = currentLength,
                onValueChange = { currentLength = it },
                label = { Text("Current Length") },
                placeholder = { Text("inches") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Text(currentFraction, modifier = Modifier.padding(16.dp))
            Box(
                modifier = modifier
            ) {
                IconButton(onClick = { currentExpanded = !currentExpanded }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Select fraction")
                }
                DropdownMenu(
                    expanded = currentExpanded,
                    onDismissRequest = { currentExpanded = false }
                ) {
                    menuItemDataMap.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.key) },
                            onClick = {
                                currentFraction = option.key
                                currentExpanded = false
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
        // desired length
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = desiredLength,
                onValueChange = { desiredLength = it },
                label = { Text("Desired Length") },
                placeholder = { Text("inches") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Text(desiredFraction, modifier = Modifier.padding(16.dp))
            Box(
                modifier = modifier
            ) {
                IconButton(onClick = { desiredExpanded = !desiredExpanded }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Select fraction")
                }
                DropdownMenu(
                    expanded = desiredExpanded,
                    onDismissRequest = { desiredExpanded = false }
                ) {
                    menuItemDataMap.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.key) },
                            onClick = {
                                desiredFraction = option.key
                                desiredExpanded = false
                                desiredLength = (desiredLength.toDouble().toInt() + option.value).toString()
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
        TextField(
            value = currentSetting,
            onValueChange = { currentSetting = it },
            label = { Text("Current Setting") },
            placeholder = { Text("inches") },
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
                desiredSetting = calculateDesiredSetting(
                    currentLength.toDouble(),
                    desiredLength.toDouble(),
                    currentSetting.toDouble()
                )
            }
        ) {
            Text("Calculate")
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        // desired setting
        Text("New Setting: $desiredSetting", fontWeight = FontWeight.Bold)
    }
}

// rounds the result to the nearest 1/8 inch
fun calculateDesiredSetting(
    currentLength: Double,
    desiredLength: Double,
    currentSetting: Double
): String {
    return (kotlin.math.round(currentSetting * desiredLength / currentLength * 8) / 8).toString()
}

@Preview(showBackground = true)
@Composable
fun SawSettingScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        SawSettingScreen(
            modifier = Modifier
        )
    }
}