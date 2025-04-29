package com.abk.kotlinopapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxM3() {
    Box {
        Box(Modifier.fillMaxSize().background(Color.White))
        Box(
            Modifier.matchParentSize().padding(top = 20.dp, bottom = 20.dp).background(Color.Yellow)
        )
        Box(Modifier.matchParentSize().padding(40.dp).background(Color.Magenta))
        Box(Modifier.align(Alignment.Center).size(300.dp, 300.dp).background(Color.Green))
        Box(Modifier.align(Alignment.TopStart).size(150.dp, 150.dp).background(Color.Red))
        Box(Modifier.align(Alignment.BottomEnd).size(150.dp, 150.dp).background(Color.Blue))
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RowM3() {
    Column {
        Row {
            Box(Modifier.size(100.dp).background(Color.Red))
            Spacer(Modifier.width(20.dp))
            Box(Modifier.size(100.dp).background(Color.Magenta))
            Spacer(Modifier.weight(1f))
            Box(Modifier.size(100.dp).background(Color.Black))
        }
        val itemsList = (0..5).toList()
        val itemsIndexedList = listOf("A", "B", "C")

        LazyColumn {
            items(itemsList.size) { Text("Item is $it") }

            item { Text("Single item") }

            itemsIndexed(itemsIndexedList) { index, item -> Text("Item at index $index is $item") }
        }
    }
}