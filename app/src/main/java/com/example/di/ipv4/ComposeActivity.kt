package com.example.di.ipv4

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.core.content.ContextCompat
import com.example.di.R

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            findMyIp(context = this)
            checkInternetConnection(this)
        }
    }
}

@Composable
fun checkInternetConnection(context: Context) {

    // A Thread that will continuously monitor the Connection Type
    Thread {
        while (true) {
            // This string is displayed when device is not connected
            // to either of the aforementioned states
            var constant = "Not Connected"

            // Invoking the Connectivity Manager
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Fetching the Network Information
            val netInfo = cm.allNetworkInfo

            // Finding if Network Info typeName is WIFI or MOBILE (Constants)
            // If found, the conStant string is supplied WIFI or MOBILE DATA
            // respectively. The supplied data is a Variable
            for (ni in netInfo) {
                if (ni.typeName.equals("WIFI", ignoreCase = true)) if (ni.isConnected) constant = "WIFI"
                if (ni.typeName.equals("MOBILE", ignoreCase = true)) if (ni.isConnected) constant = "MOBILE DATA"
            }

            // To update the layout elements in real-time, use runOnUiThread method
            // We are setting the text in the TextView as the string conState
            Log.e("CONNECTION", "checkInternetConnection: $constant")
        }
    }.start() // Starting the thread
}

@Composable
fun findMyIp(context: Context) {

    val wifiManager = ContextCompat.getSystemService(context, WifiManager::class.java)

    //  Method 1
    val ipv4 = android.text.format.Formatter.formatIpAddress(wifiManager?.connectionInfo?.ipAddress ?: 0)

    //  Method 2
    val ipAddress = wifiManager?.connectionInfo?.ipAddress ?: 0
    val ipAddressString = String.format(
        "%d.%d.%d.%d", (ipAddress and 0xFF), (ipAddress shr 8 and 0xFF), (ipAddress shr 16 and 0xFF), (ipAddress shr 24 and 0xFF)
    )

    Column {
        Greeting("Device IP Address: $ipAddressString")
        Greeting("Device IP Address: $ipv4")
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = name
    )
}

@Preview(showBackground = true, name = "dev", showSystemUi = true, device = Devices.NEXUS_5, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun Preview() {

}