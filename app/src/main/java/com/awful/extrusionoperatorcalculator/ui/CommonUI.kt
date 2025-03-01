package com.awful.extrusionoperatorcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
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

@Composable
fun ToolScreenTitleAndBackButton(
    onBack: () -> Unit,
    titleText: String
) {
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                titleText,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(
    showBackground = true
)
@Composable
fun ToolScreenTitleAndBackButtonPreview() {
    ExtrusionOperatorCalculatorTheme {
        ToolScreenTitleAndBackButton(
            onBack = {},
            titleText = "titleText",
        )
    }
}

@Composable
fun EocSettingTextFieldVM(
    initialValue: String,
    onValueChange: (String) -> Unit,
    isError: () -> Boolean,
    labelString: String,
    placeholderString: String,
    errorString: String,
    keyboardAction: ImeAction,
    onDoneAction: () -> Unit = {}
) {
    val kbController = LocalSoftwareKeyboardController.current
    TextField(
        singleLine = true,
        value = initialValue,
        onValueChange = onValueChange,
        label = { Text(labelString) },
        placeholder = { Text(placeholderString) },
        supportingText = {
            if (isError()) {
                Text(
                    text = errorString,
                    color = Color.Red
                )
            }
        },
        trailingIcon = {
            if (isError()) {
                Icon(
                    Icons.Filled.Warning,
                    stringResource(R.string.error),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = keyboardAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                kbController?.hide()
                onDoneAction()
            }
        )
    )
}

@Composable
fun EocSettingTextFieldWithFractionVM(
    initialValue: String,
    onValueChange: (String) -> Unit,
    isError: () -> Boolean,
    labelString: String,
    placeholderString: String,
    errorString: String,
    keyboardAction: ImeAction,
    onDoneAction: () -> Unit = {},
    initialFraction: String = "0",
    onFractionChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        var selectedFraction by remember { mutableStateOf(initialFraction) }
        EocSettingTextFieldVM(
            initialValue = initialValue,
            onValueChange = onValueChange,
            isError = isError,
            labelString = labelString,
            placeholderString = placeholderString,
            errorString = errorString,
            keyboardAction = keyboardAction,
            onDoneAction = onDoneAction
        )
        Text(selectedFraction, modifier = Modifier.padding(start = 16.dp))
        Box {
            var isExpanded by remember { mutableStateOf(false) }
            IconButton(onClick = { isExpanded = !isExpanded } ) {
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
                            selectedFraction = option.key
                            isExpanded = false
                            onFractionChange(selectedFraction)
                        }
                    )
                }
            }
        }
    }
}