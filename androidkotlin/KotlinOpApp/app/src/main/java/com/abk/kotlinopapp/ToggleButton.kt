
package com.abk.kotlinopapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.ToggleButtonShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(showBackground = true)
@Composable
fun ToggleButtonM3() {

    Column {
        var checked1 by remember { mutableStateOf(false) }

        val shapes =
            ToggleButtonShapes(
                shape = ToggleButtonDefaults.squareShape,
                pressedShape = ToggleButtonDefaults.pressedShape,
                checkedShape = ToggleButtonDefaults.roundShape
            )
        ToggleButton(checked = checked1, onCheckedChange = { checked1 = it }, shapes = shapes) {
            Text("Button")
        }

        var checked by remember { mutableStateOf(true) }
        val size = ButtonDefaults.MediumIconSize
        ToggleButton(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier.heightIn(size),
            shapes = ToggleButtonDefaults.shapesFor(size),
            contentPadding = ButtonDefaults.contentPaddingFor(size)
        ) {
            Icon(
                active = checked,
                activeContent =  { if (checked) Icons.Filled.Edit else Icons.Outlined.Edit},
            )
            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
            Text("Label", style = ButtonDefaults.textStyleFor(size))
        }
    }
}