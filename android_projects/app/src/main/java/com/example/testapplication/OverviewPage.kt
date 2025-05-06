package com.example.testapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel


class OverviewPage : ComponentActivity() {
    lateinit var forwardPreferences: ForwardPreferences
    lateinit var overviewPageModel: OverviewPageModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // These will stored in SharedPreferences.
        forwardPreferences = ForwardPreferences(applicationContext)

        val telegramUserApi = RetrofitHelper.getInstance(AppConstants.SERVERURL)
            .create<TelegramUserApi>(TelegramUserApi::class.java)
        val telegramRepository = TelegramRepository(telegramUserApi, applicationContext)
        // Save the default server.
        overviewPageModel = ViewModelProvider(this, OverviewPageModelFactory(telegramRepository))
            .get(OverviewPageModel::class.java)

        setContent {
            OverviewScreen(overviewPageModel)
        }
    }

//    fun userSettingsConfigured(): Boolean {
//        return when(forwardPreferences.getToken()) {
//            null -> false
//            else -> true
//        }
//    }

//    fun validateToken(token: String) {
//        val telegramUserApi = RetrofitHelper.getInstance()
//            .create<TelegramUserApi>(TelegramUserApi::class.java)
//        lifecycleScope.launch {
//            val hdrs: Map<String, String> = mapOf("Authorization" to "Basic $token",
//                "Accept" to "application/json")
//            try {
//                val result = telegramUserApi.getServerStatus(hdrs)
//                Log.d("FORWARD", "Token validation response: ${result.body().toString()}")
//            } catch(ex: Exception) {
//                ex.printStackTrace()
//            }
//        }
//    }
}

@Composable
fun OverviewScreen(overviewPageModel: OverviewPageModel = viewModel(), modifier: Modifier = Modifier) {
    val validTokenUiState by overviewPageModel.validTokenUiState.collectAsState()
    var showPassword by remember { mutableStateOf(false) }
    var showTokenDialog by remember { mutableStateOf(false) }
    val myActivity = LocalActivity.current as OverviewPage
    val context: Context = myActivity.applicationContext
    val state = remember { TextFieldState(myActivity.forwardPreferences.getToken()?:"") }

    // var enableContinue by remember { mutableStateOf(myActivity.userSettingsConfigured()) }
    Column (modifier = modifier.fillMaxSize()
        .padding(20.dp).border(5.dp, Color.Gray).background(Color.White).padding(12.dp)) {
        Text(
            text = "SMS Forward Overview",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
                    modifier = modifier.wrapContentHeight().fillMaxWidth()
                .padding(10.dp).align(Alignment.CenterHorizontally).weight(1.0f)
        )
        Text(
            text = "Forward SMS forwards the received SMS based on the forward configurations.\n\n The received " +
            "SMS can be forwarded to a Telegram user or an email.\n\n The telegram user should have enabled to " +
            "to receive messages by calling /start in the bot(gaksmsbot).\n\nUser settings requires to set the token " +
            "configured for successful forwarding of messages.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.wrapContentHeight().fillMaxWidth()
                .padding(10.dp).align(Alignment.CenterHorizontally).weight(4.0f)
        )
        Row (
            modifier = modifier.fillMaxWidth().padding(10.dp).weight(1.0f)
        ){
            OutlinedButton(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f),
                onClick = {
                    showTokenDialog = true
                }
            ) {
                Text(text = "Configure")
            }
            OutlinedButton(
                enabled = validTokenUiState.validToken, //enableContinue && validTokenUiState.validToken,
                onClick = {
                    showTokenDialog = false
                    myActivity.startActivity(Intent(myActivity, ForwardConfigPage::class.java))
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = "Continue")
            }
        }
    }
    if (showTokenDialog) {
        AlertDialog(
            onDismissRequest = {},
            text = {
                Column {
                    Text(
                        text = "SMS Forward User Token",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = modifier.wrapContentHeight().fillMaxWidth()
                            .padding(2.dp).align(Alignment.CenterHorizontally)
                    )

                    BasicSecureTextField(
                        state = state,
                        textObfuscationMode =
                            if (showPassword) {
                                TextObfuscationMode.Visible
                            } else {
                                TextObfuscationMode.RevealLastTyped
                            },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                            .padding(6.dp),
                        decorator = { innerTextField ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(start = 16.dp, end = 48.dp)
                                ) {
                                    innerTextField()
                                }
                                Icon(
                                    if (showPassword) {
                                        Icons.Filled.Visibility
                                    } else {
                                        Icons.Filled.VisibilityOff
                                    },
                                    contentDescription = "Toggle password visibility",
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .requiredSize(48.dp).padding(16.dp)
                                        .clickable { showPassword = !showPassword }
                                )
                            }
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    when(state.text) {
                        "" -> Toast.makeText(context, "Invalid token", Toast.LENGTH_SHORT).show()
                        else -> {
                            // myActivity.forwardPreferences.saveToken(state.text.toString())
                            Log.d("FORWARD", "State Input: " + state.text)
                            // enableContinue = myActivity.userSettingsConfigured()
                           //  myActivity.validateToken(state.text.toString())
                            overviewPageModel.updateServerStatus(state.text.toString())
                        }
                    }
                    showTokenDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {showTokenDialog = false}) {
                    Text("Cancel")
                }
            }
        )
    }
}

