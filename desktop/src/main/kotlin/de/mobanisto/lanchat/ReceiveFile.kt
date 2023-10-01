package de.mobanisto.lanchat

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import java.net.ServerSocket
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption
import kotlin.io.path.absolute

class ReceiveFile : CliktCommand(name = "receive-file") {

    private val port: Int by option(
        "--port",
        help = "Port to receive files on"
    ).int().default(5001)

    private val output by argument().path()

    override fun run() {
        println(output.absolute())
        ServerSocket(port).use { serverSocket ->
            println("Waiting for incoming file...")
            val socket = serverSocket.accept()
            println("Receiving file...")
            val input = socket.getInputStream()
            FileChannel.open(
                output,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
            ).use { channel ->
                Channels.newChannel(input).use { inputChannel ->
                    val transferred = channel.transferFrom(inputChannel, 0, Int.MAX_VALUE.toLong())
                    println(String.format("Received %d bytes", transferred))
                }
            }
        }
    }
}
