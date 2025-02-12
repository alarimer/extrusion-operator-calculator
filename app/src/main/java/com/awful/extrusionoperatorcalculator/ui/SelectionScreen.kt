package com.awful.extrusionoperatorcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.awful.extrusionoperatorcalculator.R
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable

@Serializable
object SelectionScreen

@Composable
fun SelectionScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(SawSettingScreen) }
        ) {
            Text(stringResource(R.string.saw_setting))
        }
        Spacer(
            modifier = Modifier.padding(4.dp)
        )
        Button(
            onClick = { navController.navigate(RackTimeScreen) }
        ) {
            Text(stringResource(R.string.rack_time))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        SelectionScreen(
            navController = rememberNavController(),
            modifier = Modifier.padding()
        )
    }
}