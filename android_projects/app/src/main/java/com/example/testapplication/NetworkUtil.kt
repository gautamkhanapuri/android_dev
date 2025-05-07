package com.example.testapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


object NetworkUtil {
    const val TYPE_WIFI: Int = 1
    const val TYPE_MOBILE: Int = 2
    const val TYPE_NOT_CONNECTED: Int = 0
    const val NETWORK_STATUS_NOT_CONNECTED: Int = 0
    const val NETWORK_STATUS_WIFI: Int = 1
    const val NETWORK_STATUS_MOBILE: Int = 2

    fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // NetworkCapabilities to check what type of
        // network has the internet connection

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = cm.activeNetwork ?: return TYPE_NOT_CONNECTED

        // Representation of the capabilities of an active network.
        val activeNetwork = cm.getNetworkCapabilities(network) ?: return TYPE_NOT_CONNECTED
        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> TYPE_WIFI

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> TYPE_MOBILE

            // else return false
            else -> TYPE_NOT_CONNECTED
        }
    }

    fun getConnectivityStatusString(context: Context): Int {
        val conn = getConnectivityStatus(context)
        var status = 0
        if (conn == TYPE_WIFI) {
            status = NETWORK_STATUS_WIFI
        } else if (conn == TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED
        }
        return status
    }
}