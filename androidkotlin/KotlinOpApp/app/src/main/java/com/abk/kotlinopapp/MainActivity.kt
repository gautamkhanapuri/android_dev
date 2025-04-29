package com.abk.kotlinopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abk.kotlinopapp.ui.theme.KotlinOpAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinOpAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OpApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class Profile(var isActive: Boolean, var imageURL: String, val name: String, var role: String)

@Composable
fun ProfileApp(obj: Profile, modifier: Modifier = Modifier) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.baseline_attribution_24),
            contentDescription = "Application"
        )
        Column(modifier = modifier.fillMaxWidth()) {
            Text(text = "Name: ${obj.name}", modifier = modifier.fillMaxWidth())
            Text(text = "Role: ${obj.role}", modifier=modifier.padding(top=10.dp).fillMaxWidth())
            if (obj.isActive) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth()){
                    Text(text="Active")
                    Checkbox(checked = true, onCheckedChange = {})
                }
            }
        }
    }
}

@Composable
fun OpApp(modifier: Modifier = Modifier) {
    // val profile = Profile(true, "https://cheezycode.com/", "John", "Developer")

    Column (modifier = modifier.fillMaxSize().padding(5.dp).border(3.dp, Color.Blue).padding(12.dp)) {
        var textA by remember { mutableStateOf("0.0") }
        var textB by remember { mutableStateOf("1.0") }
        var result by remember { mutableStateOf(0.00) }

        TextField(
            value = textA,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { textA = it },
            label = { Text("Enter a") },
            modifier = modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        )
        OutlinedTextField(
            value = textB,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { textB = it },
            label = { Text("Enter b") },
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        )
        Row (
            modifier = modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        ){
            OutlinedButton(
                onClick = {
                    result = textA.toDouble() + textB.toDouble()
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = "+")
            }
            OutlinedButton(
                onClick = {
                    result = textA.toDouble() - textB.toDouble()
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = "-")
            }
            OutlinedButton(
                onClick = {
                    result = textA.toDouble() * textB.toDouble()
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = "*")
            }
            OutlinedButton(
                onClick = {
                    result = textA.toDouble()/textB.toDouble()
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = "/")
            }
        }
        Text(
            text = "Result is ${result}",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.wrapContentHeight().fillMaxWidth().padding(20.dp).align(Alignment.CenterHorizontally)
        )
        // ProfileApp(profile)
    }

}

@Preview(showBackground = true, name="Check")
@Composable
fun OpAppPreview() {
    KotlinOpAppTheme {
        OpApp()
    }
}
