package de.mobanisto.lanchat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val messages = remember { mutableStateListOf<Message>() }
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ComposeUI(modifier = Modifier.fillMaxSize(), messages = messages, sendMessage = ::sendMessage)
                }
            }
        }
    }

    fun sendMessage(message: String) {

    }
}

