package com.abk.kotlinopapp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableInferredTarget
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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
            Box(Modifier
                .fillMaxSize()
                .padding(2.dp)
                .background(linear)
            ) {
                Text("Clickable", Modifier.align(Alignment.Center).background(linear, shape= AbsoluteCutCornerShape(4.dp)).padding(8.dp))
            }
        }

        OutlinedCard(
            Modifier
                .size(
                    width = 180.dp,
                    height = 100.dp
                )
                .padding(4.dp)
                .background(color=Color.Gray)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .background(linear)
            ) {
                Text(
                    "Card content",
                    color=Color.Black,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            linear,
                            shape = RoundedCornerShape(4.dp))
                        .padding(10.dp)
                )
            }
        }


        Column(
            modifier = Modifier.size(
                width = 180.dp,
                height = 500.dp
            )
                .padding(2.dp)
        ) {
            // Create a linear gradient that shows red in the top left corner
            // and blue in the bottom right corner
            val linear = Brush. linearGradient(listOf(Color. Red, Color. Blue))
            Box(modifier = Modifier. size(180.dp, 100.dp).background(linear))
            {
                val gradientBrush = Brush. horizontalGradient(
                    colors = listOf(Color. Red, Color. Blue, Color. Green),
                    startX = 0.0f,
                    endX = 500.0f )
                Text(
                    text = "Text with gradient back",
                    modifier = Modifier
                        .align(Alignment.Center)
                        . background(
                            brush = gradientBrush,
                            shape = CutCornerShape(8.dp))
                        .padding(10.dp),
                    color = Color.White)
            }
            Spacer(modifier = Modifier. size(10.dp))
            // Create a radial gradient centered about the drawing area that is green on
            // the outer
            // edge of the circle and magenta towards the center of the circle
            val radial = Brush. radialGradient(listOf(Color. Green, Color.Blue))
            Box(modifier = Modifier. size(180.dp).background(radial))
            Spacer(modifier = Modifier. size(10.dp))
            // Create a sweep gradient centered about the drawing area that is cyan at
            // the start angle and magenta towards the end angle.
            val sweep = Brush. sweepGradient(listOf(Color. Cyan, Color. Magenta))
            Box(modifier = Modifier. size(180.dp).background(sweep))
        }
    }
}
