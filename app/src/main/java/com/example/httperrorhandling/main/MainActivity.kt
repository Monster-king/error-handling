package com.example.httperrorhandling.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.httperrorhandling.message.MessageController
import com.example.httperrorhandling.ui.theme.HttpErrorHandlingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var messageController: MessageController

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToMessages()
        val viewModel: MainViewModel by viewModels()
        setContent {
            HttpErrorHandlingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val state by viewModel.uiState.collectAsState()
                    Column {
                        state.user?.let { user ->
                            Text(
                                text = user.name
                            )
                        }
                        Button(
                            modifier = Modifier.wrapContentSize(),
                            onClick = { viewModel.testUserInfo() }
                        ) {
                            Text("Check response")
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToMessages() {
        scope.launch {
            messageController.observeMessage()
                .collect {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}