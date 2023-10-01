package de.mobanisto.lanchat

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.net.InetAddress

class ReceiveMessages : CliktCommand(name = "receive") {

    private val port: Int by option(
        "--port",
        help = "Port to receive message on"
    ).int().default(5000)

    override fun run() {
        val receiver = Receiver(port) { source: InetAddress?, message: String? ->
            println(String.format("received from %s: %s", source, message))
        }
        receiver.run()
    }
}
