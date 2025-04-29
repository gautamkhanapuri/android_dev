package com.abk.kotlinopapp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CircularProgressIndictorM3() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()

        var progress by remember { mutableStateOf(0.34f) }

        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(progress = { animatedProgress })
            Spacer(Modifier.requiredHeight(30.dp))
            Text("Set progress:")
            Slider(
                modifier = Modifier.width(300.dp),
                value = progress,
                valueRange = 0f..1f,
                onValueChange = { progress = it },
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) { CircularWavyProgressIndicator() }
    }
}

