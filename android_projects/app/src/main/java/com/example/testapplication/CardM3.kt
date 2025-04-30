package com.example.testapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableInferredTarget
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun CardM3() {
    Column {
        val l1 = Brush.linearGradient(listOf(Color(0xFF1D2671), Color(0xFFC33764)))
        val linear = Brush.linearGradient(listOf(Color.DarkGray, Color.LightGray))

        Card(
            Modifier.size(width = 180.dp, height = 100.dp)
        )
        {
            Box(Modifier.fillMaxSize().padding(2.dp).background(l1)) {
                Text(
                    text = "Card content",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Card(
            onClick = { /* Do something */ },
            modifier = Modifier.size(width = 180.dp, height = 100.dp)
        ) {
            Box(Modifier.fillMaxSize().padding(2.dp).background(linear)) { Text("Clickable", Modifier.align(Alignment.Center)) }
        }

        OutlinedCard(Modifier.size(width = 180.dp, height = 100.dp).padding(2.dp).background(color=Color.Gray)) {
            Box(Modifier.fillMaxSize().padding(2.dp).background(linear)) { Text("Card content", color=Color.White, modifier = Modifier.align(Alignment.Center)) }
        }
    }
}