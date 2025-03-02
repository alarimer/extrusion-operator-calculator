package com.awful.extrusionoperatorcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import com.awful.extrusionoperatorcalculator.ui.AboutScreen
import com.awful.extrusionoperatorcalculator.ui.RackTimeScreen
import com.awful.extrusionoperatorcalculator.ui.SawSettingScreen
import com.awful.extrusionoperatorcalculator.ui.SelectionScreen
import com.awful.extrusionoperatorcalculator.ui.SpeedChangeScreen
import com.awful.extrusionoperatorcalculator.ui.WeatherstripTimeScreen
import com.awful.extrusionoperatorcalculator.ui.WeightCalculationScreen
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExtrusionOperatorCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EocApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EocApp(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    modifier: Modifier
) {
    val navController = rememberNavController()
    val isWideDisplay =
        windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM
                || windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
    NavHost(
        navController = navController,
        startDestination = SelectionScreen,
        modifier = modifier
    ) {
        composable<SelectionScreen> {
            SelectionScreen(
                isWideDisplay = isWideDisplay,
                navController = navController,
                modifier = modifier
            )
        }
        composable<SawSettingScreen> {
            SawSettingScreen(
                isWideDisplay = isWideDisplay,
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
        composable<WeightCalculationScreen> {
            WeightCalculationScreen(
                isWideDisplay = isWideDisplay,
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
        composable<SpeedChangeScreen> {
            SpeedChangeScreen(
                isWideDisplay = isWideDisplay,
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
        composable<RackTimeScreen> {
            RackTimeScreen(
                isWideDisplay = isWideDisplay,
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
        composable<WeatherstripTimeScreen> {
            WeatherstripTimeScreen(
                isWideDisplay = isWideDisplay,
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
        composable<AboutScreen> {
            AboutScreen(
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EocAppPreview() {
    ExtrusionOperatorCalculatorTheme {
        EocApp(
            modifier = Modifier
        )
    }
}