package de.mobanisto.lanchat

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class SendMessage : CliktCommand(name = "send") {

    private val port: Int by option(
        "--port",
        help = "Port to send message on"
    ).int().default(5000)

    private val message by argument()

    override fun run() {
        val sender = Sender(port)
        val addresses = sender.listAllBroadcastAddresses()
        for (address in addresses) {
            sender.broadcast(message, address)
        }
    }
}
