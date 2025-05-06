package com.example.testapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider


class ForwardConfigPage : ComponentActivity() {
    lateinit var forwardConfigViewModel: ForwardConfigViewModel

//    lateinit var fwdList1: LiveData<List<ForwardConfig>>
//    var fwdList = mutableListOf<ForwardConfig>(
//        ForwardConfig(1, "Water Supply", "9880055602", null, "kbajey", true, 4),
//        ForwardConfig(2, "Bank credited with ", "IX-IXC", "kbajey@gmail.com", null, true, 10),
//        ForwardConfig(3, "Paper Supply", "9880055603", null, "ajeybk", true, 6),
//        ForwardConfig(4, "Wallet debited with ", "IX-PAYTM", "ajeybk1@gmail.com", null, true, 12),
//        ForwardConfig(5, "Classes for AI/ML", "9448292324", null, "gak97_97", true, 1),
//        ForwardConfig(6, "Invitation for meeting", "IM-MSCSDEPT", "gautamajey@gmail.com", null, true, 1),
//        ForwardConfig(7, "Real Estate Auction", "8792255603", null, "kbajey", true, 4),
//        ForwardConfig(8, "Member Invitation for event", "IX-MEMYNK", "kbajey@gmail.com", null, true, 10),
//        ForwardConfig(9, "Group Meeting on", "9880055602", null, "ajeybk1", true, 4),
//        ForwardConfig(10, "Results announced for ", "IX-IXC", "kbajey@gmail.com", null, true, 10),
//    )


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val startForResult = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val create = intent?.getBooleanExtra("CREATE", false)
            val fwdCfg: ForwardConfig? = intent?.getParcelableExtra("FORWARD-CONFIG", ForwardConfig::class.java)
            val logStr = if (create == true) "Created" else "Updated config"
            Log.d("FORWARD", "$logStr: $fwdCfg")
            if (create!!) {
                forwardConfigViewModel.insertForwardConfig(fwdCfg!!)
            } else {
                forwardConfigViewModel.updateForwardConfig(fwdCfg!!)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forwardConfigDao =
            ForwardDatabase.getDatabase(applicationContext).forwardConfigDao()
        val forwardConfigRepository = ForwardConfigRepository(forwardConfigDao)
        forwardConfigViewModel =
            ViewModelProvider(this, ForwardConfigViewModelFactory(forwardConfigRepository))
                .get(ForwardConfigViewModel::class.java)

        setContent {
            ForwardConfigView(forwardConfigViewModel)
        }

    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForwardConfigView(forwardConfigViewModel: ForwardConfigViewModel,
                      modifier: Modifier = Modifier) {


    val myActivity = LocalActivity.current as ForwardConfigPage
    var fabHeight by remember { mutableIntStateOf(0) }
    val heightInDp = with(LocalDensity.current) { fabHeight.toDp() }

    val forwardConfigUiState by forwardConfigViewModel.forwardConfigUiState.collectAsState()
    val forwardConfigList: List<ForwardConfig> = forwardConfigUiState.forwardConfigList

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.onGloballyPositioned {
                    fabHeight = it.size.height
                },
                shape = CircleShape,
                onClick = {
                    val intent = Intent(myActivity, ForwardConfigCreate::class.java)
                    // navigate to the detail screen, show a dialog, etc.
                    myActivity.startForResult.launch(intent)

                },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "icon")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom=heightInDp + 16.dp, start=10.dp, top=10.dp, end = 10.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            start = 10.dp,
                            bottom = 5.dp,
                            end = 10.dp
                        )
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        "Forward Configurations",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(18.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            items(forwardConfigList) { cfg ->
                ForwardConfigCard(forwardConfig = cfg, onItemClick = { forwardConfig ->
                    // Handle item click
                    val intent = Intent(myActivity, ForwardConfigCreate::class.java)
                    intent.putExtra("FORWARD-CONFIG", forwardConfig)
                    // navigate to the detail screen, show a dialog, etc.
                    myActivity.startForResult.launch(intent)

                })
            }
        }
    }
}

@Composable
fun ForwardConfigCard(forwardConfig: ForwardConfig, onItemClick: (ForwardConfig) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = {
                onItemClick(forwardConfig)
            })
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.padding(8.dp)) {
                Text(text = forwardConfig.message?: "-")
                Text(text = forwardConfig.fromPhone?: "<>")
                Text(text =
                    forwardConfig.email
                        ?: (forwardConfig.telegram ?: "-")
                )
                Text(text = forwardConfig.count.toString())
            }
        }
    }
}

