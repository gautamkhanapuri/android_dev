package com.example.testapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OverviewPageModelFactory(
    private val telegramRepository: TelegramRepository)
    :  ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OverviewPageModel(telegramRepository) as T
    }
}