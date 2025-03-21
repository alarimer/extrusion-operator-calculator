package com.awful.extrusionoperatorcalculator.ui

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
    isWideDisplay: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isWideDisplay) {
            Row(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
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
                        onClick = { navController.navigate(WeightCalculationScreen) }
                    ) {
                        Text(stringResource(R.string.weight_calculation))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    Button(
                        onClick = { navController.navigate(SpeedChangeScreen) }
                    ) {
                        Text(stringResource(R.string.speed_change))
                    }
                }
                Spacer(
                    modifier = Modifier.padding(16.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { navController.navigate(RackTimeScreen) }
                    ) {
                        Text(stringResource(R.string.rack_time))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    Button(
                        onClick = { navController.navigate(WeatherstripTimeScreen) }
                    ) {
                        Text(stringResource(R.string.weatherstrip_calculation))
                    }
                    Spacer(
                        modifier = Modifier.padding(4.dp)
                    )
                    Button(
                        onClick = { navController.navigate(WeightChangeEcsScreen) }
                    ) {
                        Text(stringResource(R.string.weight_change_ecs))
                    }
                }
            }
        } else {
            Button(
                onClick = { navController.navigate(SawSettingScreen) }
            ) {
                Text(stringResource(R.string.saw_setting))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Button(
                onClick = { navController.navigate(WeightCalculationScreen) }
            ) {
                Text(stringResource(R.string.weight_calculation))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Button(
                onClick = { navController.navigate(SpeedChangeScreen) }
            ) {
                Text(stringResource(R.string.speed_change))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Button(
                onClick = { navController.navigate(RackTimeScreen) }
            ) {
                Text(stringResource(R.string.rack_time))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Button(
                onClick = { navController.navigate(WeatherstripTimeScreen) }
            ) {
                Text(stringResource(R.string.weatherstrip_calculation))
            }
            Spacer(
                modifier = Modifier.padding(4.dp)
            )
            Button(
                onClick = { navController.navigate(WeightChangeEcsScreen) }
            ) {
                Text(stringResource(R.string.weight_change_ecs))
            }
        }
        Spacer(
            modifier = Modifier.padding(32.dp)
        )
        Button(
            onClick = { navController.navigate(AboutScreen) }
        ) {
            Text(stringResource(R.string.about_label))
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
fun SelectionScreenPreviewPortrait() {
    ExtrusionOperatorCalculatorTheme {
        SelectionScreen(
            isWideDisplay = false,
            navController = rememberNavController(),
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
fun SelectionScreenPreviewLandscape() {
    ExtrusionOperatorCalculatorTheme {
        SelectionScreen(
            isWideDisplay = true,
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}