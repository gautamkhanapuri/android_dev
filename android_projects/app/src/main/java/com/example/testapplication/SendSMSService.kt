package com.example.testapplication

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.lang.Exception


class SendSMSService: LifecycleService() {
    lateinit var database: TestAppDatabase

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val action = intent?.getExtras()!!.getString("action")
        if (action.equals("newmessage", ignoreCase = true)) {
            val telegramUserApi =
                RetrofitHelper.getInstance(AppConstants.SERVERURL)
                    .create<TelegramUserApi>(TelegramUserApi::class.java)
            val telegramRepository =
                TelegramRepository(telegramUserApi, applicationContext)
            database = TestAppDatabase.getDatabase(applicationContext)
            val smsString = intent.getExtras()!!.getString("smsstring")
            val smsAddress = intent.getExtras()!!.getString("smsfrom")

            lifecycleScope.launch {
                try {
                    val fwdList = database.forwardDao().getForwards()
                    // val fwdList = fwds.value
                    fwdList?.size?.let {
                        if (it > 0) {
                            for (fwdCfg in fwdList) {
                                if (fwdCfg.isActive) {
                                    if (smsString!!.contains(fwdCfg.message)) {
                                        val updateCfg = Forwards(
                                            id = fwdCfg.id,
                                            message = fwdCfg.message,
                                            fromPhone = fwdCfg.fromPhone,
                                            email = fwdCfg.email,
                                            telegram = fwdCfg.telegram,
                                            isActive = fwdCfg.isActive,
                                            numberOfMessagesForwarded = fwdCfg.numberOfMessagesForwarded + 1
                                        )
                                        database.forwardDao().updateForward(updateCfg)
                                        val forwardMessage = ForwardMessage(
                                            id=0,
                                            forwardId = fwdCfg.id,
                                            message = smsString,
                                            fromPhone = smsAddress,
                                            email = fwdCfg.email,
                                            telegram = fwdCfg.telegram,
                                            status = true
                                        )
                                        database.forwardMessageDao().insertForwardMessage(forwardMessage)
                                        val tgm = if (fwdCfg.telegram.startsWith("@"))
                                            fwdCfg.telegram.slice(1..(fwdCfg.telegram.length-1))
                                        else
                                            fwdCfg.telegram
                                        val sendMessageBody = SendMessageBody(
                                            message = smsString,
                                            from = smsAddress!!,
                                            email = fwdCfg.email,
                                            telegram = tgm
                                        )
                                        telegramRepository.sendMessage(sendMessageBody)
                                    }
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
//        val status: Int = NetworkUtil.getConnectivityStatusString(this)
//        if (status == NetworkUtil.NETWORK_STATUS_MOBILE ||
//            status == NetworkUtil.NETWORK_STATUS_WIFI
//        ) {
//            // val sharedPreferences = this.getSharedPreferences(App.MYPREFS, MODE_PRIVATE)
//            // val serverurl = sharedPreferences.getString("server", PreferenceUtil.SERVERURL)
//            // JSONAsyncTask().execute(serverurl)
//        }
        return START_NOT_STICKY
    }
}