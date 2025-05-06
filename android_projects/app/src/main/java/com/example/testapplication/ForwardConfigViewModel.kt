package com.example.testapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ForwardConfigUiState(val forwardConfigList: List<ForwardConfig> = listOf())

class ForwardConfigViewModel(private val forwardConfigRepository: ForwardConfigRepository)
    : ViewModel(){

    val forwardConfigUiState: StateFlow<ForwardConfigUiState> =
        forwardConfigRepository.getForwardConfigs().map {
            ForwardConfigUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ForwardConfigUiState()
    )

    fun updateForwardConfig(forwardConfig: ForwardConfig) {
        viewModelScope.launch {
            forwardConfigRepository.updateForwardConfig(forwardConfig)
        }
    }

    fun deleteForwardConfig(forwardConfig: ForwardConfig) {
        viewModelScope.launch {
            forwardConfigRepository.deleteForwardConfig(forwardConfig)
        }
    }

    fun insertForwardConfig(forwardConfig: ForwardConfig) {
        viewModelScope.launch {
            forwardConfigRepository.insertForwardConfig(forwardConfig)
        }
    }
}

