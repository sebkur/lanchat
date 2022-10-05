package de.mobanisto.lanchat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ComposeUI(modifier: Modifier = Modifier, messages: MutableList<Message>, sendMessage: (String) -> Unit) {
    Scaffold(modifier, bottomBar = { MessageInput(sendMessage) }) { padding ->
        Messages(padding, messages)
    }
}

@Composable
private fun Messages(padding: PaddingValues, messages: List<Message>) {
    val scrollState = rememberLazyListState(
        if (messages.isNotEmpty()) messages.lastIndex
        else 0
    )
    Box(modifier = Modifier.fillMaxSize().padding(padding)) {
        LazyColumn(state = scrollState, modifier = Modifier.padding(PaddingValues(8.dp))) {
            items(items = messages) { m ->
                SelectionContainer {
                    Text("${m.source}: ${m.message}", modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth())
                }
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )
    }
}

@Composable
private fun MessageInput(sendMessage: (String) -> Unit) {
    val (value, setValue) = remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        Divider()
        Text("Enter some text and press 'send' to broadcast message on the LAN:")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value,
                setValue,
                placeholder = { Text("Type a message") },
                modifier = Modifier.weight(1f, true)
            )
            Button({ sendMessage(value) }) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text("Send")
            }
        }
    }
}
