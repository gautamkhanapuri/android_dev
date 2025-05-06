package com.example.testapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.testapplication.NetworkUtil.getConnectivityStatusString


class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val status = getConnectivityStatusString(context)
        // println(intent.getAction())
        if ("android.intent.action.BOOT_COMPLETED" != intent.getAction()) {
            if (status == NetworkUtil.NETWORK_STATUS_MOBILE ||
                status == NetworkUtil.NETWORK_STATUS_WIFI
            ) {
                val i = Intent(context, SendSMSService::class.java)
                i.putExtra("action", "internetconnected")
                context.startService(i)
            }
        }
    }
}