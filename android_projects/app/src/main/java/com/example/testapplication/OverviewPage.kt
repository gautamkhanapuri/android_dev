package com.example.testapplication

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.livedata.observeAsState

class OverviewPage : ComponentActivity() {
    lateinit var database: TestAppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            TestAppDatabase::class.java,
            "testAppDb"
        ).build()
        val dao = database.forwardDao()
        setContent {
            val viewModel: ForwardViewModel = ViewModelProvider(this,
                ForwardViewModelFactory(dao))[ForwardViewModel::class.java]
            val navController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = { SnackbarHost(snackBarHostState) }
            ){ innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "overview",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("overview") {
                        OverviewScreen(
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
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        val forward = viewModel.forwards.collectAsState().value.find {it.id == id}
                        forward?.let {
                            EditForwardScreen(
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
fun OverviewScreen(
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
    onCancel: () -> Unit,
    onSave: (Forwards) -> Unit,
) {
    var message by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telegram by remember { mutableStateOf("") }

    var isMessageValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isTelegramValid by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Create a Forward Configuration",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
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

        // Forward message field
        InputFieldWithHelp(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            label = "Email",
            helpText = "Enter a valid email (e.g., name@example.com).",
            isValid = isMessageValid
        )

        // Telegram ID Field
        InputFieldWithHelp(
            value = telegram,
            onValueChange = {
                telegram = it
                isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
            },
            label = "Telegram",
            helpText = "Enter a Telegram ID starting with '@'.",
            isValid = isTelegramValid
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (isMessageValid && isEmailValid && isTelegramValid) {
                        val newForward = Forwards(
                            id = 0,
                            message = message.trim(),
                            email = email.trim(),
                            telegram = telegram.trim(),
                            isActive = true,
                            numberOfMessagesForwarded = 0
                        )
                        onSave(newForward)
                    } else {
                        isMessageValid = message.isNotBlank()
                        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        isTelegramValid = telegram.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
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
fun EditForwardScreen(
    forward: Forwards,
    onCancel: () -> Unit,
    onUpdate: (Forwards) -> Unit
) {
    var email by remember { mutableStateOf(forward.email) }
    var telegram by remember { mutableStateOf(forward.telegram) }

    var isEmailValid by remember { mutableStateOf(true) }
    var isTelegramValid by remember { mutableStateOf(true) }

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

        // Editable Email Field
        InputFieldWithHelp(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = "Email",
            helpText = "Enter a valid email (e.g., name@example.com).",
            isValid = isEmailValid
        )

        // Editable Telegram ID Field
        InputFieldWithHelp(
            value = telegram,
            onValueChange = {
                telegram = it
                isTelegramValid = it.matches(Regex("^@[a-zA-Z0-9_]{5,}$"))
            },
            label = "Telegram",
            helpText = "Enter a Telegram ID starting with '@'.",
            isValid = isTelegramValid
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Green),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                ),
                onClick = {
                    if (isEmailValid && isTelegramValid) {
                        onUpdate(
                            forward.copy(
                                email = email.trim(),
                                telegram = telegram.trim()
                            )
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Confirm Edit",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
            OutlinedButton(
                border = BorderStroke(1.dp, Color.Red),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                ),
                onClick = onCancel) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = "Cancel",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
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
    isValid: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {Text(label)},
            isError =  !isValid,
            modifier = Modifier
                .weight(1f)
                .border(
                    BorderStroke(
                        1.dp,
                        if (!isValid) Color.Red else MaterialTheme.colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.medium
                ),
            singleLine = true
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
            Spacer(modifier = Modifier.height(4.dp))
            Text("Email: ${forward.email}")
            Text("Telegram: ${forward.telegram}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onEdit(forward)}) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Button")
                    }
                    IconButton(onClick = { onDelete(forward) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = forward.isActive, onCheckedChange = {
                        onToggle(forward.copy(isActive = it))
                    })
                    Text("${forward.numberOfMessagesForwarded}")
                }
            }
        }
    }
}

class ForwardViewModel(private val dao: ForwardDao) : ViewModel() {
    private val _forwards: MutableStateFlow<List<Forwards>> =
        MutableStateFlow<List<Forwards>>(emptyList())
    val forwards = _forwards.asStateFlow()

    init {
        dao.getAllMessages().observeForever {
            _forwards.value = it
        }
    }

    fun toggleActive(updated: Forwards) {
        viewModelScope.launch {
            dao.updateForward(updated)
        }
    }

    fun delete(item: Forwards) {
        viewModelScope.launch {
            dao.deleteMessage(item)
        }
    }

    fun add(forward: Forwards) {
        viewModelScope.launch {
            dao.insertForward(forward)
        }
    }

    fun update(updated: Forwards) {
        viewModelScope.launch {
            dao.updateForward(updated)
        }
    }
}

class ForwardViewModelFactory(private val dao: ForwardDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel > create(modelClass: Class<T>): T {
        return ForwardViewModel(dao) as T
    }
}

@Preview(showBackground = true)
@Composable
fun ForwardCardPreview() {
    ForwardCard(
        forward = Forwards(
            id = 1,
            message = "Kindly note that the meeting is rescheduled.",
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