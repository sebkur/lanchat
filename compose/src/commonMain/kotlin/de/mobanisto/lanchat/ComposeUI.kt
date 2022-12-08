package de.mobanisto.lanchat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText

@Composable
fun ComposeUI(
    modifier: Modifier = Modifier,
    messages: MutableList<Message>,
    sendMessage: (String) -> Unit,
    onLinkClicked: ((String) -> Unit)? = null
) {
    Scaffold(modifier, bottomBar = { MessageInput(sendMessage) }) { padding ->
        Messages(padding, messages, onLinkClicked)
    }
}

@Composable
private fun Messages(padding: PaddingValues, messages: List<Message>, onLinkClicked: ((String) -> Unit)?) {
    val scrollState = rememberLazyListState(
        if (messages.isNotEmpty()) messages.lastIndex
        else 0
    )
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Box(modifier = Modifier.fillMaxSize().padding(padding)) {
        LazyColumn(state = scrollState, modifier = Modifier.padding(PaddingValues(8.dp))) {
            items(items = messages) { m ->
                Row {
                    // Not using SelectionContainer here as this prevents links from bing clickable.
                    // See https://github.com/JetBrains/compose-jb/issues/1450 for details.
                    RichText(modifier = Modifier.padding(vertical = 8.dp).weight(1f)) {
                        Markdown("${m.source}: ${m.message}", onLinkClicked = onLinkClicked)
                    }
                    IconButton(onClick = { clipboardManager.setText(AnnotatedString((m.message))) }) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = null,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                        )
                    }
                }
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
                modifier = Modifier.weight(1f, true).onPreviewKeyEvent {
                    if (it.type == KeyEventType.KeyDown && it.key == Key.Enter && it.isCtrlPressed) {
                        sendMessage(value)
                        setValue("")
                        return@onPreviewKeyEvent true
                    }
                    false
                }
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
