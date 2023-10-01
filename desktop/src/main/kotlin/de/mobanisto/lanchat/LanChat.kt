package de.mobanisto.lanchat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import java.awt.Desktop
import java.net.URI
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) = LanChat().subcommands(
    SendMessage(),
    ReceiveMessages(),
    SendFile(),
    ReceiveFile(),
).main(args)

private class LanChat : CliktCommand(
    name = "Lanchat",
    help = "Lanchat - an insecure (W)LAN messenger",
    invokeWithoutSubcommand = true
) {

    private val version by option(
        "--version",
        help = "Show version and exit"
    ).flag(default = false)

    override fun run() {
        val versionCode = Version.getVersion()
        println("LanChat version $versionCode")

        val subcommand = currentContext.invokedSubcommand
        if (subcommand != null) {
            return
        }

        if (version) {
            exitProcess(0)
        }

        val greeting = Message("System", "Welcome to LanChat version $versionCode")
        application {
            Window(
                onCloseRequest = ::exitApplication,
                title = "LanChat",
                icon = painterResource("lanchat.png")
            ) {
                val messages = remember { mutableStateListOf(greeting) }
                thread {
                    val receiver = Receiver(5000) { source, message ->
                        messages.add(Message(source.toString(), message))
                    }
                    receiver.run()
                }
                ComposeUI(
                    messages = messages,
                    sendMessage = ::sendMessage,
                    onLinkClicked = ::linkClicked
                )
            }
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
