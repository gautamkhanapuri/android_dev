package com.example.testapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ForwardConfigCreateViewModelFactory(
    val telegramRepository: TelegramRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForwardConfigCreateViewModel(telegramRepository) as T
    }
}