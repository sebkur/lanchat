package de.mobanisto.lanchat

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import java.io.OutputStream
import java.net.Socket
import java.nio.channels.Channels
import java.nio.channels.FileChannel

class SendFile : CliktCommand(name = "send-file") {

    private val port: Int by option(
        "--port",
        help = "Port to send files on"
    ).int().default(5001)

    private val ip by argument()
    private val input by argument().path(mustExist = true)

    override fun run() {
        println("Connecting...")
        Socket(ip, port).use { socket ->
            println("Sending file...")
            val os: OutputStream = socket.getOutputStream()
            val channel = Channels.newChannel(os)
            val inputChannel = FileChannel.open(input)
            val transferred = inputChannel.transferTo(0, Int.MAX_VALUE.toLong(), channel)
            println(String.format("Sent %d bytes", transferred))
        }
    }
}
