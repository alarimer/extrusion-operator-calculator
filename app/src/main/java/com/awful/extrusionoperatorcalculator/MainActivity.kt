package com.awful.extrusionoperatorcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awful.extrusionoperatorcalculator.ui.RackTimeScreen
import com.awful.extrusionoperatorcalculator.ui.SawSettingScreen
import com.awful.extrusionoperatorcalculator.ui.SelectionScreen
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
    modifier: Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SelectionScreen,
        modifier = modifier
    ) {
        composable<SelectionScreen> {
            SelectionScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable<SawSettingScreen> {
            SawSettingScreen(
                onBack = { navController.navigateUp() },
                modifier = modifier
            )
        }
        composable<RackTimeScreen> {
            RackTimeScreen(
                onBack = { navController.navigateUp() },
                modifier =  modifier
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