package de.mobanisto.lanchat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Desktop
import java.net.URI
import kotlin.concurrent.thread


fun main() {
    val version = Version.getVersion()
    println("LanChat version $version")
    application {
        Window(onCloseRequest = ::exitApplication, title = "LanChat", icon = painterResource("lanchat.png")) {
            val messages = remember { mutableStateListOf<Message>() }
            thread {
                val receiver = Receiver(5000) { source, message ->
                    messages.add(Message(source.toString(), message))
                }
                receiver.run()
            }
            ComposeUI(messages = messages, sendMessage = ::sendMessage, onLinkClicked = ::linkClicked)
        }
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

private fun linkClicked(link: String) {
    val desktop = Desktop.getDesktop()
    try {
        desktop.browse(URI(link))
    } catch (e: Throwable) {
        println("Error while opening link '$link'")
        e.printStackTrace()
    }
}

