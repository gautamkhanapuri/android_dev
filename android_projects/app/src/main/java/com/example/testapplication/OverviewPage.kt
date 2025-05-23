package com.example.testapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OverviewPage : ComponentActivity() {

    private lateinit var database: TestAppDatabase
    private lateinit var overviewPageModel: OverviewPageModel
    lateinit var forwardPreferences: ForwardPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        database = Room.databaseBuilder(
//                applicationContext,
//                TestAppDatabase::class.java,
//                "testAppDb"
//            ).fallbackToDestructiveMigration(false).build()

        forwardPreferences = ForwardPreferences(applicationContext)
        val telegramUserApi =
            RetrofitHelper.getInstance(AppConstants.SERVERURL)
                .create<TelegramUserApi>(TelegramUserApi::class.java)
        val telegramRepository =
            TelegramRepository(telegramUserApi, applicationContext)
        overviewPageModel =
            ViewModelProvider(this, OverviewPageModelFactory(telegramRepository))[OverviewPageModel::class.java]

//        database = Room.databaseBuilder(
//            applicationContext,
//            TestAppDatabase::class.java,
//            "testAppDb"
//        ).build()

        database = TestAppDatabase.getDatabase(applicationContext)
        val dao = database.forwardDao()

        setContent {
            val viewModel: ForwardViewModel =
                ViewModelProvider(
                    this,
                    ForwardViewModelFactory(
                        dao,
                        telegramRepository
                    )
                )[ForwardViewModel::class.java]
            val navController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = { SnackbarHost(snackBarHostState) }
            ){ innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "overview",
                    modifier = Modifier.padding(innerPadding)
                )   {
                    composable("overview") {
                        OverviewScreen(
                            overviewPageModel,
                            context = applicationContext,
                            snackBarHostState = snackBarHostState,
                            navController = navController
                        )
                    }
                    composable("forward") {
                        ForwardScreen(
                            viewModel,
                            onAddClick = {
                                navController.navigate("create")
                            },
                            snackBarHostState = snackBarHostState,
                            navController = navController
                        )
                    }
                    composable("create") {
                        CreateForwardScreen(
                            viewModel,
                            onCancel = {navController.popBackStack()},
                            onSave = {
                                viewModel.add(it)
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("snackbar_success", true)
                                navController.popBackStack()
                            },
                        )
                    }
                    composable("edit/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
                        val forward = viewModel.forwards.collectAsState().value.find {it.id == id}
                        forward?.let {
                            EditForwardScreen(
                                viewModel,
                                forward = it,
                                onCancel = {navController.popBackStack()},
                                onUpdate = {updated ->
                                    viewModel.update(updated)
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("snackbar_edit", true)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForwardScreen(
    viewModel: ForwardViewModel,
    onAddClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    navController: NavController
) {
    val showSuccessSnackbar = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Boolean>("snackbar_success")
        ?.observeAsState()

    LaunchedEffect(showSuccessSnackbar?.value) {
        if (showSuccessSnackbar?.value == true) {
            snackBarHostState.showSnackbar("Forward config successfully created")
            // Clear the flag so it doesn't show again on recomposition
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("snackbar_success", false)
        }
    }

    val showEditSnackbar = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Boolean>("snackbar_edit")
        ?.observeAsState()

    LaunchedEffect(showEditSnackbar?.value) {
        if (showEditSnackbar?.value == true) {
            snackBarHostState.showSnackbar("Configuration successfully updated")
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("snackbar_edit", false)
        }
    }
    val forwards by viewModel.forwards.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Forward Configuration"
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "All Forward Configurations",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(10.dp)) {
            items(forwards) { forward ->
                ForwardCard(
                    forward = forward,
                    onToggle = {
                        viewModel.toggleActive(it)
                    },
                    onDelete = {
                        viewModel.delete(it)
                    },
                    onEdit = {
                        navController.navigate("edit/${it.id}")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun CreateForwardScreen(
    viewModel: ForwardViewModel,
    onCancel: () -> Unit,
    onSave: (Forwards) -> Unit,
) {
    var message by remember { mutableStateOf("") }
    var senderFrom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telegram by remember { mutableStateOf("") }

    var isMessageValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isTelegramValid by remember { mutableStateOf(true) }
    var selectedMode by remember { mutableStateOf("Email") }

    val validUserUiState by viewModel.validUserUiState.collectAsState()
    viewModel.validateUser(telegram, false)

    // val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            "Create a Forward Configuration",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        // Message Field
        InputFieldWithHelp(
            value = message,
            onValueChange = {
                message = it
                isMessageValid = message.isNotBlank()
            },
            label = "Message",
            helpText = "Enter the message that will be forwarded.",
            isValid = isMessageValid
        )

        // From Field
        InputFieldWithHelp(
            value = senderFrom,
            onValueChange = {senderFrom = it},
            label = "From",
            helpText = "Enter sender information (if any).",
            isValid = true
        )

        Text("Select Forward Mode:", fontWeight = FontWeight.SemiBold)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Email Option
                RadioButton(
                    selected = selectedMode == "Email",
                    onClick = {selectedMode = "Email"}
                )
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email option",
                    tint = if (selectedMode == "Email") MaterialTheme.colorScheme.primary else
                        Color.Gray
                )
                Text("Email")
            }
            // Telegram Option
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMode == "Telegram",
                    onClick = { selectedMode = "Telegram" }
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Telegram",
                    tint = if (selectedMode == "Telegram") MaterialTheme.colorScheme.primary
                    else Color.Gray
                )
                Text("Telegram")
            }
        }

        // Email field
        InputFieldWithHelp(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            label = "Email",
            helpText = "Enter a valid email (e.g., name@example.com).",
            isValid = isEmailValid,
            enabled = selectedMode == "Email"
        )

        // Telegram ID Field
        InputFieldWithHelp(
            value = telegram,
            onValueChange = {
                telegram = it
                isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
                viewModel.validateUser(telegram, false)
            },
            label = "Telegram",
            helpText = "Enter a Telegram ID starting with '@'.",
            isValid = isTelegramValid && validUserUiState.validUser,
            enabled = selectedMode == "Telegram"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (isMessageValid) {
                        if (selectedMode == "Email") {
                            if (isEmailValid) {
                                onSave(
                                    Forwards(
                                        id = 0,
                                        message = message.trim(),
                                        sender = senderFrom,
                                        email = email.trim(),
                                        telegram = "",
                                        isActive = true,
                                        numberOfMessagesForwarded = 0
                                    )
                                )
                            } else {
                                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            }
                        } else if (selectedMode == "Telegram") {
                            if (isTelegramValid) {
                                onSave(
                                    Forwards(
                                        id = 0,
                                        message = message.trim(),
                                        sender = senderFrom,
                                        email = "",
                                        telegram = telegram.trim(),
                                        isActive = true,
                                        numberOfMessagesForwarded = 0
                                    )
                                )
                            } else {
                                isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
                            }
                        }
                    } else {
                        isMessageValid = message.isNotBlank()
                        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
                    }
                }
//                    if (isMessageValid && isEmailValid && isTelegramValid) {
//                        val newForward = Forwards(
//                            id = 0,
//                            message = message.trim(),
//                            sender = senderFrom,
//                            email = email.trim(),
//                            telegram = telegram.trim(),
//                            isActive = true,
//                            numberOfMessagesForwarded = 0
//                        )
//                        onSave(newForward)
//                    } else {
//                        isMessageValid = message.isNotBlank()
//                        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
//                        isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
//                    }
            ) {
                Text("Save")
            }
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun EditForwardScreen(
    viewModel: ForwardViewModel,
    forward: Forwards,
    onCancel: () -> Unit,
    onUpdate: (Forwards) -> Unit
) {
    var email by remember { mutableStateOf(forward.email) }
    var telegram by remember { mutableStateOf(forward.telegram) }

    var isEmailValid by remember { mutableStateOf(true) }
    var isTelegramValid by remember { mutableStateOf(true) }

    var selectedMode by remember {
        mutableStateOf(if (forward.email.isNotBlank()) "Email" else "Telegram")
    }

    val validUserUiState by viewModel.validUserUiState.collectAsState()
    viewModel.validateUser(telegram, false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Edit Configuration",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        // Non-editable Message Field
        OutlinedTextField(
            value = forward.message,
            onValueChange = {},
            label = { Text("Message") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        // Non-editable From Field
        OutlinedTextField(
            value = forward.sender,
            onValueChange = {},
            label = { Text("From") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        // Communication Method Selector
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedMode == "Email",
                onClick = { selectedMode = "Email" }
            )
            Icon(
                Icons.Default.Email,
                contentDescription = "Email",
                tint = if (selectedMode == "Email") Color(0xFF4285F4) else Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedMode == "Telegram",
                onClick = { selectedMode = "Telegram" }
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send, // use your own icon
                contentDescription = "Telegram",
                tint = if (selectedMode == "Telegram") Color(0xFF0088CC) else Color.Gray
            )
        }

        // Editable Email Field
        InputFieldWithHelp(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = "Email",
            helpText = "Enter a valid email (e.g., name@example.com).",
            isValid = isEmailValid,
            enabled = selectedMode == "Email"
        )

        // Editable Telegram ID Field
        InputFieldWithHelp(
            value = telegram,
            onValueChange = {
                telegram = it
                isTelegramValid = it.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
                viewModel.validateUser(telegram, false)
            },
            label = "Telegram",
            helpText = "Enter a Telegram ID starting with '@'.",
            isValid = isTelegramValid && validUserUiState.validUser,
            enabled = selectedMode == "Telegram"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val isValid = when (selectedMode) {
                        "Email" -> {
                            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            isEmailValid
                        }
                        "Telegram" -> {
                            isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
                            isTelegramValid
                        }
                        else -> false
                    }

                    if (isValid) {
                        onUpdate(
                            forward.copy(
                                email = if (selectedMode == "Email") email.trim() else "",
                                telegram = if (selectedMode == "Telegram") telegram.trim() else ""
                            )
                        )
                    }
                }
            ) {
                Text("Save")
            }
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun InputFieldWithHelp(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    helpText: String,
    isValid: Boolean,
    enabled: Boolean = true
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {Text(label)},
            isError =  !isValid,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (!isValid) Color.Red else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (!isValid) Color.Red else MaterialTheme.colorScheme.primary,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            ),
            modifier = Modifier.weight(1f),
            singleLine = true,
            enabled = enabled
        )
        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {showDialog = true}) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("?", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false},
            confirmButton = {
                TextButton(onClick = {showDialog = false}) {
                    Text("OK")
                }
            },
            title = { Text(label) },
            text = {Text(helpText)}
        )
    }
}

@Composable
fun ForwardCard(
    forward: Forwards,
    onToggle: (Forwards) -> Unit,
    onDelete: (Forwards) -> Unit,
    onEdit: (Forwards) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (forward.isActive) Color.White else Color.Gray)
            .clickable {expanded = !expanded},
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Message:", fontWeight = FontWeight.Bold)
            Text(
                text = forward.message,
                maxLines = if (expanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "From: ${forward.sender.ifBlank { "Not provided" }}",
                color = if (forward.sender.isBlank()) Color.Gray else Color.Unspecified
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email
            Text(
                text = "Email: ${forward.email.ifBlank { "Not provided" }}",
                color = if (forward.email.isBlank()) Color.Gray else Color.Unspecified
            )

            // Telegram
            Text(
                text = "Telegram: ${forward.telegram.ifBlank { "Not provided" }}",
                color = if (forward.telegram.isBlank()) Color.Gray else Color.Unspecified
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(onClick = { onEdit(forward)}) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Button")
                    }
                    var showDeleteDialog by remember { mutableStateOf(false) }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Confirm Deletion") },
                            text = { Text(
                                "Are you sure you want to delete this forward configuration?")
                                   },
                            confirmButton = {
                                TextButton(onClick = {
                                    onDelete(forward)
                                    showDeleteDialog = false
                                }) {
                                    Text("Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                    Switch(
                        checked = forward.isActive,
                        onCheckedChange = {
                            onToggle(forward.copy(isActive = it))
                        }
                    )
                }
                Row(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${forward.numberOfMessagesForwarded}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

class ForwardViewModel(
    private val dao: ForwardDao,
    private val telegramRepository: TelegramRepository
) : ViewModel() {

    private val TAG: String = "ForwardVM"
    private val _forwards: MutableStateFlow<List<Forwards>> =
        MutableStateFlow(emptyList())
    val forwards = _forwards.asStateFlow()

    val validUserUiState: StateFlow<ValidUserUiState> = telegramRepository.validUserUiState

    init {
        // This also needs to be a coroutine.
        dao.getAllMessages().observeForever {
            _forwards.value = it
        }
    }

    fun toggleActive(updated: Forwards) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardViewModel Coroutine toggleActive dao - Started")
            dao.updateForward(updated)
        }
    }

    fun delete(item: Forwards) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardViewModel Coroutine delete dao - Started")
            dao.deleteMessage(item)
        }
    }

    fun add(forward: Forwards) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardViewModel Coroutine add dao - Started")
            dao.insertForward(forward)
        }
    }

    fun update(updated: Forwards) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardViewModel Coroutine update dao - Started")
            dao.updateForward(updated)
        }
    }

    fun validateUser(username: String, email: Boolean) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardViewModel Coroutine validateTelegramUser - Started")
            telegramRepository.validateUser(username, email)
        }
    }

    fun sendMessage(sendMessageBody: SendMessageBody) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardViewModel Coroutine sendMessage - Started")
            telegramRepository.sendMessage(sendMessageBody)
        }
    }


}

class ForwardViewModelFactory(
    private val dao: ForwardDao,
    private val telegramRepository: TelegramRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel > create(modelClass: Class<T>): T {
        return ForwardViewModel(dao, telegramRepository) as T
    }
}

@Preview(showBackground = true)
@Composable
fun ForwardCardPreview() {
    ForwardCard(
        forward = Forwards(
            id = 1,
            message = "Kindly note that the meeting is rescheduled.",
            sender = "Khoury College",
            email = "xyz@gmail.com",
            telegram = "@example",
            isActive = true,
            numberOfMessagesForwarded = 42
        ),
        onEdit = {},
        onDelete = {},
        onToggle = {}
    )
}

@Composable
fun OverviewScreen(
    overviewPageModel: OverviewPageModel,
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavController
) {
    val validTokenUiState by overviewPageModel.validTokenUiState.collectAsState()
    var showPassword by remember { mutableStateOf(false) }
    var showTokenDialog by remember { mutableStateOf(false) }
    val myActivity = LocalActivity.current as OverviewPage
    // val context: Context = myActivity.applicationContext
    val state = remember { TextFieldState(myActivity.forwardPreferences.getToken()?:"") }
    overviewPageModel.updateServerStatus(state.text.toString())

    Column (modifier = Modifier.fillMaxSize()
        .padding(20.dp).border(5.dp, Color.Gray).background(Color.White).padding(12.dp)) {
        Text(
            text = "SMS Forward Overview",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight().fillMaxWidth()
                .padding(10.dp).align(Alignment.CenterHorizontally).weight(1.0f)
        )
        Text(
            text = "Forward SMS forwards the received SMS based on the forward configurations.\n\n The received " +
                    "SMS can be forwarded to a Telegram user or an email.\n\n The telegram user should have enabled to " +
                    "to receive messages by calling /start in the bot(gaksmsbot).\n\nUser settings requires to set the token " +
                    "configured for successful forwarding of messages.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight().fillMaxWidth()
                .padding(10.dp).align(Alignment.CenterHorizontally).weight(4.0f)
        )
        Row (
            modifier = Modifier.fillMaxWidth().padding(10.dp).weight(1.0f)
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
                enabled = validTokenUiState.validToken,
                onClick = {
                    showTokenDialog = false
                    navController.navigate("forward")
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
                        modifier = Modifier.wrapContentHeight().fillMaxWidth()
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
                            Log.d("FORWARD", "State Input: " + state.text)
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