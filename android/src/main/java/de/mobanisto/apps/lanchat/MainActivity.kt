package de.mobanisto.apps.lanchat

import android.content.Context
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.MulticastLock
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import de.mobanisto.lanchat.ComposeUI
import de.mobanisto.lanchat.Message
import de.mobanisto.lanchat.Receiver
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.Objects.requireNonNull
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var lock: MulticastLock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("activity", "onCreate()")

        setContent {
            val messages = remember { mutableStateListOf<Message>() }
            thread {
                val wifiManager = requireNonNull(applicationContext.getSystemService(WIFI_SERVICE) as WifiManager)
                lock = requireNonNull(wifiManager).createMulticastLock("multicastLock")
                lock.setReferenceCounted(false)
                lock.acquire()

                val receiver = Receiver(5000) { source, message ->
                    messages.add(Message(source.toString(), message))
                }
                receiver.run()
            }
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ComposeUI(modifier = Modifier.fillMaxSize(), messages = messages, sendMessage = ::sendMessage)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("activity", "onDestroy()")
        lock.release()
    }

    private fun sendMessage(message: String) {
        // Allow network on main thread
        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())

        val socket = DatagramSocket()
        socket.broadcast = true
        val buffer = message.toByteArray()
        val packet = DatagramPacket(buffer, buffer.size, getBroadcastAddress(), 5000)
        socket.send(packet)
        socket.close()
    }

    private fun getBroadcastAddress(): InetAddress {
        val wifi = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcp = wifi.dhcpInfo
        val broadcast = dhcp.ipAddress and dhcp.netmask or dhcp.netmask.inv()
        val quads = ByteArray(4)
        for (k in 0..3) quads[k] = (broadcast shr k * 8 and 0xFF).toByte()
        return InetAddress.getByAddress(quads)
    }
}

