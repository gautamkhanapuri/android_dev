package com.example.testapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.widget.Toast

class SMSBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        if (context == null) return
        val bundle = intent.extras ?: return

        val pdus = bundle["pdus"] as Array<*>?
        val messages = arrayOfNulls<SmsMessage>(pdus!!.size)
        val format = bundle.getString("format")

        var text = ""

        pdus.indices.forEachIndexed { index, _ ->
            messages[index] = SmsMessage.createFromPdu(pdus[index] as ByteArray, format)
            text += messages[index]!!.messageBody
        }

        var iccStr = messages[0]!!.indexOnIcc
        var address = messages[0]!!.originatingAddress

        val i = Intent(context, SendSMSService::class.java)
        i.putExtra("action", "newmessage")
        i.putExtra("smsstring", text)
        i.putExtra("smsfrom", address)
        i.putExtra("smsicc", iccStr)
        context.startService(i)

        Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    }
}
