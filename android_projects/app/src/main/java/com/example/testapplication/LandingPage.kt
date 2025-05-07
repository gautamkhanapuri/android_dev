package com.example.testapplication

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LandingPageActivity : AppCompatActivity() {

    private val requestPermissionCode = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()

        setContent {
            LandingPage {
                startActivity(Intent(this, OverviewPage::class.java))
                finish()
            }
        }
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
            ), requestPermissionCode
        )
    }
}

@Preview
@Composable
fun LandingPage(onAuthSuccess: () -> Unit = {}) {
    var showPinDialog by remember { mutableStateOf(false) }
    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.gak_logo
                ),
                contentDescription = "GAK Solutions Logo.",
                modifier = Modifier
                    .size(200.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )

            Button(
                onClick = {
                    val authHandler = AuthHandler(
                        context as AppCompatActivity,
                        { onAuthSuccess() },
                        { showPinDialog = true }
                    )
                    authHandler.authenticate()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    "Authenticate", fontSize = 18.sp, color = Color.Black,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
        if (showPinDialog) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Enter PIN") },
                text = {
                    Column {
                        TextField(
                            value = pinInput,
                            onValueChange = { pinInput = it },
                            label = { Text(text = "PIN") },
                            visualTransformation = PasswordVisualTransformation(),
                            isError = pinError
                        )
                        if (pinError) {
                            Text("Incorrect PIN", color = Color.Red)
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (pinInput == "1234") {
                            pinError = false
                            showPinDialog = false
                            onAuthSuccess()
                        } else {
                            pinError = true
                        }
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPinDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LandingPagePreview() {
//    LandingPage(authenticateOnClick = {})
//}
