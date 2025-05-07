package com.example.testapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class OverviewPageModel(private val telegramRepository: TelegramRepository): ViewModel() {
    private val TAG: String = "FSMS-OVERVIEWVM"
    val validTokenUiState: StateFlow<ValidTokenUiState> = telegramRepository.validTokenUiState

    init {
        Log.d(TAG, "OverviewPageModel init - Started")
        updateServerStatus()

    }

    fun updateServerStatus(token: String? = null) {
        viewModelScope.launch {
            Log.d(TAG, "OverviewPageModel Coroutine updateServerStatus - Started")
            telegramRepository.updateServerStatus(token)
        }
    }
}