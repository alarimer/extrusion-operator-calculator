package com.awful.extrusionoperatorcalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awful.extrusionoperatorcalculator.BuildConfig
import com.awful.extrusionoperatorcalculator.R
import com.awful.extrusionoperatorcalculator.ui.theme.ExtrusionOperatorCalculatorTheme
import kotlinx.serialization.Serializable

@Serializable
object AboutScreen

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // title and back button
        ToolScreenTitleAndBackButton(
            onBack = onBack,
            titleText = stringResource(R.string.about_title)
        )
        // information
        Text(stringResource(R.string.app_description))
        Spacer(modifier = modifier.height(16.dp))
        val githubLink = buildAnnotatedString {
            append("See ")
            withLink(
                link = LinkAnnotation.Url(
                    url = "https://github.com/alarimer",
                    styles = TextLinkStyles(SpanStyle(color = Color.Blue))
                )
            ) {
                append("GitHub")
            }
            append(" for more information.")
        }
        Text(githubLink)
        Spacer(modifier = modifier.height(16.dp))
        Text("Version: " + BuildConfig.VERSION_NAME)
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AboutScreenPreview() {
    ExtrusionOperatorCalculatorTheme {
        AboutScreen(
            onBack = {}
        )
    }
}