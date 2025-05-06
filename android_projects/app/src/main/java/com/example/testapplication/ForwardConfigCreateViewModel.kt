package com.example.testapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ForwardConfigCreateViewModel(private val telegramRepository: TelegramRepository):
    ViewModel() {

    private val TAG: String = "FSMS-FWDCFGCREATEVM"
    val validUserUiState: StateFlow<ValidUserUiState> = telegramRepository.validUserUiState

    fun validateUser(username: String, email: Boolean) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardConfigCreateViewModel Coroutine validateTelegramUser - Started")
            telegramRepository.validateUser(username, email)
        }
    }

    fun sendMessage(forwardConfig: ForwardConfig) {
        viewModelScope.launch {
            Log.d(TAG, "ForwardConfigCreateViewModel Coroutine sendMessage - Started")
            telegramRepository.sendMessage(forwardConfig)
        }
    }
}