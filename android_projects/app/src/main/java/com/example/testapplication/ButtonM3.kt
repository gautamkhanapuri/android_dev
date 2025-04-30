package com.example.testapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Start
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ButtonM3() {

    Column {
        var clickCount by remember { mutableStateOf(10) }
        Button(onClick = {clickCount++}) { Text("Clicked $clickCount") }


        Button(
            onClick = { /* Do something! */ },
            shape = ButtonDefaults.squareShape
        ) { Text("Button") }

        Button(
            onClick = { /* Do something! */ },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
        ) {
            Icon(
                Icons.TwoTone.Star,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Like")
        }

        Button(onClick = { /* Do something! */ }, contentPadding = ButtonDefaults.SmallContentPadding) {
            Text("Button")
        }

        val size = ButtonDefaults.ExtraSmallContainerHeight
        Button(
            onClick = { /* Do something! */ },
            modifier = Modifier.heightIn(size),
            contentPadding = ButtonDefaults.contentPaddingFor(size)
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.iconSizeFor(size))
            )
            Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
            Text("Label")
        }

        Button(onClick = {}, shapes = ButtonDefaults.shapes()) { Text("Button") }
    }


}