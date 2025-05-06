package com.example.testapplication

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


class ForwardConfigRepository(private val forwardConfigDao: ForwardConfigDao) {

//    fun getForwardConfig(active: Int): LiveData<List<ForwardConfig>> {
//        return forwardConfigDao.getForwardConfig(active)
//    }
//
//    fun getAllForwardConfig(): LiveData<List<ForwardConfig>> {
//        return forwardConfigDao.getAllForwardConfig()
//    }

    fun getForwardConfigs(): Flow<List<ForwardConfig>> {
        return forwardConfigDao.getForwardConfigs()
    }

    suspend fun updateForwardConfig(forwardConfig: ForwardConfig) {
        forwardConfigDao.updateForwardConfig(forwardConfig)
    }

    suspend fun deleteForwardConfig(forwardConfig: ForwardConfig) {
        forwardConfigDao.deleteForwardConfig(forwardConfig)
    }

    suspend fun insertForwardConfig(forwardConfig: ForwardConfig) {
        forwardConfigDao.insertForwardConfig(forwardConfig)
    }
}