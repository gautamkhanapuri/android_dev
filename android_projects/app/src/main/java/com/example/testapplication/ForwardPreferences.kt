package com.example.testapplication

import android.content.Context
import androidx.core.content.edit

class ForwardPreferences(context: Context) {

    private val MYPREFS = "config.data"

    // private val SERVERKEY = "SERVER"
    private val USERTOKEN = "TOKEN"

    // private val SERVERURL = "http://localhost:8000"
    // const val SERVERURL = "https://gak.solutions

    // val USERPATH = "/forward/api/user"
    // val MESSAGEPATH = "/forward/api/message"
    // private val AUTH = "YWpleWJrOiNrYjMyMSEj"

    private val sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE)

    fun getToken(): String? {
        return sharedPreferences.getString(USERTOKEN, null)
    }

//    fun getServer(): String {
//        return sharedPreferences.getString(SERVERKEY, null) ?: SERVERURL
//    }

    fun saveToken(token: String) {
        sharedPreferences.edit(commit = true) {
            putString(USERTOKEN, token)
        }
    }

//    fun saveServer(server: String?) {
//        sharedPreferences.edit(commit = true) {
//            putString(SERVERKEY, server?:SERVERURL)
//        }
//    }

}