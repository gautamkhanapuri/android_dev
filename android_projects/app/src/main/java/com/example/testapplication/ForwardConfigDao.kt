package com.example.testapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ForwardConfigDao {

    @Insert
    suspend fun insertForwardConfig(forwardConfig: ForwardConfig)

    @Update
    suspend fun updateForwardConfig(forwardConfig: ForwardConfig)

    @Delete
    suspend fun deleteForwardConfig(forwardConfig: ForwardConfig)

    @Query("SELECT * FROM forwardconfig")
    fun getForwardConfigs(): Flow<List<ForwardConfig>>

}