package com.abk.kotlinopapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.twotone.AddHome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(showBackground = true)
@Composable
fun ElevatedButtonM3() {
    Column {
        ElevatedButton(onClick = {}, shapes = ButtonDefaults.shapes()) { Text("Elevated Button") }

        Spacer(Modifier.height(30.dp))

        FilledIconButton(
            onClick = { /* doSomething() */ },
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(
                Icons.TwoTone.AddHome,
                contentDescription = "Localized description"
            )
        }

        var checked1 by remember { mutableStateOf(false) }

        FilledIconToggleButton(checked = checked1, onCheckedChange = { checked1 = it }) {
            if (checked1) {
                Icon(Icons.Filled.Lock, contentDescription = "Localized description")
            } else {
                Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
            }
        }

        FilledTonalButton(onClick = {}, shapes = ButtonDefaults.shapes()) {
            Text("Filled Tonal Button")
        }


        FilledTonalIconButton(onClick = { /* doSomething() */ }, shapes = IconButtonDefaults.shapes()) {
            Icon(Icons.Filled.Lock, contentDescription = "Localized description")
        }

        var checked by remember { mutableStateOf(false) }
        FilledTonalIconToggleButton(
            checked = checked,
            onCheckedChange = { checked = it },
            shapes = IconButtonDefaults.toggleableShapes()
        ) {
            if (checked) {
                Icon(Icons.Filled.Lock, contentDescription = "Localized description")
            } else {
                Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
            }
        }

        OutlinedButton(onClick = {}, shapes = ButtonDefaults.shapes()) { Text("Outlined Button") }

    }
}