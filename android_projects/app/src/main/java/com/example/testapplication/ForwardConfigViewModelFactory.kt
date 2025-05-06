package com.example.testapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ForwardConfigViewModelFactory(
    private val forwardConfigRepository: ForwardConfigRepository):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForwardConfigViewModel(forwardConfigRepository) as T
    }
}