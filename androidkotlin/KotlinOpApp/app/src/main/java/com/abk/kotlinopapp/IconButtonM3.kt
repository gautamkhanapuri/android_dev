package com.abk.kotlinopapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(showBackground = true)
@Composable
fun IconButtonM3(){
    Column {
        OutlinedIconButton(
            onClick = { /* doSomething() */ },
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(Icons.Filled.Lock, contentDescription = "Localized description")
        }

        var checked by remember { mutableStateOf(false) }
        OutlinedIconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
            if (checked) {
                Icon(Icons.Filled.Lock, contentDescription = "Localized description")
            } else {
                Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
            }
        }

        var checked1 by remember { mutableStateOf(false) }
        OutlinedIconToggleButton(
            checked = checked1,
            onCheckedChange = { checked1 = it },
            shapes = IconButtonDefaults.toggleableShapes()
        ) {
            if (checked1) {
                Icon(Icons.Filled.Lock, contentDescription = "Localized description")
            } else {
                Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
            }
        }

        var state by remember { mutableStateOf(true) }
        Row(Modifier.selectableGroup()) {
            RadioButton(
                selected = state,
                onClick = { state = true },
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
            RadioButton(
                selected = !state,
                onClick = { state = false },
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
        }


        val radioOptions = listOf("Calls", "Missed", "Friends")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier.fillMaxWidth()
                        .height(28.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
    }
}