package com.example.testapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.squareShape
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TextButtonM3() {
    TextButton(
        onClick = {},
        // TODO: shape parameter is supported as well as shapes.
        shapes = ButtonDefaults.shapes(squareShape),
        colors = ButtonDefaults.textButtonColors(Color.Black),
        border = BorderStroke(2.dp, Color.Green)
    ) {
        Text(
            "Text Button"
        )
    }
}