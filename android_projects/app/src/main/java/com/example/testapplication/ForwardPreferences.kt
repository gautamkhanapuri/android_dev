package com.example.testapplication

import android.content.Context
import androidx.core.content.edit

class ForwardPreferences(context: Context) {

    private val MYPREFS = "config.data"
    private val USERTOKEN = "TOKEN"
    private val sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE)

    fun getToken(): String? {
        return sharedPreferences.getString(USERTOKEN, null)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit(commit = true) {
            putString(USERTOKEN, token)
        }
    }

}