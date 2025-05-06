package com.example.testapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class ForwardConfigCreate: ComponentActivity() {

    lateinit var forwardConfigCreateViewModel: ForwardConfigCreateViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forwardConfig: ForwardConfig? = intent
            .getParcelableExtra("FORWARD-CONFIG", ForwardConfig::class.java)

        val telegramUserApi = RetrofitHelper.getInstance(AppConstants.SERVERURL)
            .create<TelegramUserApi>(TelegramUserApi::class.java)
        val telegramRepository = TelegramRepository(telegramUserApi, applicationContext)
        // Save the default server.
        forwardConfigCreateViewModel =
            ViewModelProvider(
                this, ForwardConfigCreateViewModelFactory(
                    telegramRepository
                )
            ).get(ForwardConfigCreateViewModel::class.java)


        setContent {
            ForwardConfigCreateView(forwardConfig, forwardConfigCreateViewModel)
        }
    }
}

@Composable
fun ForwardConfigCreateView(
        forwardConfig: ForwardConfig?,
        forwardConfigCreateViewModel: ForwardConfigCreateViewModel = viewModel(),
        modifier: Modifier = Modifier) {

    val validUserUiState by forwardConfigCreateViewModel.validUserUiState.collectAsState()

    val myActivity = LocalActivity.current as ForwardConfigCreate
    val create = forwardConfig == null

    val sendFormat =
        if (forwardConfig?.telegram != null) {
            "Telegram"
        } else if (forwardConfig?.email != null) {
            "Email"
        } else {
            ""
        }
    var textMessage by remember { mutableStateOf(forwardConfig?.message?:"") }
    var textFrom by remember { mutableStateOf(forwardConfig?.fromPhone ?: "")}
    var textSend by remember { mutableStateOf(
        if (sendFormat == "Telegram") {
            forwardConfig?.telegram
        } else if (sendFormat == "Email") {
            forwardConfig?.email
        } else {
            ""
        }
    ) }
    val radioOptions = listOf("Telegram", "Email")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(
        if (sendFormat == "Telegram") {
            radioOptions[0]
        } else if (sendFormat == "Email") {
            radioOptions[1]
        } else {
            radioOptions[0]
        }
    ) }

    if (forwardConfig != null){
        forwardConfigCreateViewModel.validateUser(
            textSend!!,
            selectedOption == "Email"
        )
    }

    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        Text(
            text = if (forwardConfig != null) "SMS Forward Config Edit" else "SMS Forward Config Create",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = modifier.wrapContentHeight()
                .fillMaxWidth().align(Alignment.CenterHorizontally)
                .padding(top=30.dp, bottom= 10.dp)
        )
        OutlinedTextField(
            value = textMessage,
            singleLine = false,
            enabled = create,
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (create) {
                    textMessage = it
                }
            },
            label = { Text(text = "Message Pattern", fontSize = 16.sp) },
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        )
        OutlinedTextField(
            value = textFrom,
            singleLine = true,
            enabled = create,
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (create) {
                    textFrom = it
                }
            },
            label = { Text(text = "From", fontSize = 16.sp) },
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        )
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Row(modifier.selectableGroup().padding(5.dp)) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .wrapContentWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                forwardConfigCreateViewModel.validateUser(
                                    textSend!!,
                                    text == "Email"
                                )
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 10.dp).weight(1.0f).align(Alignment.CenterVertically),
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null, // null recommended for accessibility with screen readers
                        modifier = Modifier.padding(start = 5.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 5.dp).align(Alignment.CenterVertically)
                    )
                }
            }
        }
        OutlinedTextField(
            value = textSend!!,
            enabled = true,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                textSend = it
                forwardConfigCreateViewModel.validateUser(
                    textSend!!,
                    selectedOption == "Email"
                )
            },
            label = { Text(text = selectedOption, fontSize = 16.sp) },
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        )
        Row (
            modifier = modifier.wrapContentHeight().fillMaxWidth().padding(10.dp)
        ){
            OutlinedButton(
                onClick = {
                    val resultIntent = Intent()
                    myActivity.setResult(Activity.RESULT_CANCELED, resultIntent)
                    myActivity.finish()
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = "Cancel")
            }
            OutlinedButton(
                enabled = if (validUserUiState.validUser) {
                    if (create) {
                        textMessage != "" && textFrom != "" && textSend != ""
                    } else {
                        true
                    }
                } else {
                    false
               },
                onClick = {
                    val resultIntent = Intent()
                    val fwdCfg: ForwardConfig
                    if (create) {
                        fwdCfg = ForwardConfig(
                            0,
                            textMessage,
                            textFrom,
                            if (selectedOption == "Email") textSend else null,
                            if (selectedOption == "Telegram") textSend else null,
                            1,
                            0
                        )
                    } else {
                        fwdCfg = ForwardConfig(
                            forwardConfig.id,
                            forwardConfig.message,
                            forwardConfig.fromPhone,
                            if (selectedOption == "Email") textSend else null,
                            if (selectedOption == "Telegram") textSend else null,
                            forwardConfig.active,
                            forwardConfig.count
                        )
                    }
                    forwardConfigCreateViewModel.sendMessage(forwardConfig!!)
                    resultIntent.putExtra("CREATE", create)
                    resultIntent.putExtra("FORWARD-CONFIG", fwdCfg)
                    myActivity.setResult(Activity.RESULT_OK, resultIntent)
                    myActivity.finish()
                },
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp).weight(1.0f)
            ) {
                Text(text = if (create) "Create" else "Edit")
            }
        }
    }
}