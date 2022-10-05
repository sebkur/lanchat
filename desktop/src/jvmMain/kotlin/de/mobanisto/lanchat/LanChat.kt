package de.mobanisto.lanchat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.singleWindowApplication
import kotlin.concurrent.thread

fun main() {
    singleWindowApplication(title = "Lanchat") {
        val messages = remember { mutableStateListOf<Message>() }
        thread {
            val receiver = Receiver(5000) { source, message ->
                messages.add(Message(source.toString(), message))
            }
            receiver.run()
        }
        ComposeUI(messages = messages, sendMessage = ::sendMessage)
    }
}

private fun sendMessage(message: String) {
    if (message.isBlank()) return
    println("sending: $message")
    val sender = Sender(5000)
    val addresses = sender.listAllBroadcastAddresses()
    for (address in addresses) {
        sender.broadcast(message, address)
    }
}

