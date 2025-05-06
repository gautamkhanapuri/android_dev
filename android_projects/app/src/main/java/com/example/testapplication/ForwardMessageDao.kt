package com.example.testapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForwardMessageDao {

    @Insert
    suspend fun insertForwardMessage(forwardMessage: ForwardMessage)

    @Update
    suspend fun updateForwardMessage(forwardMessage: ForwardMessage)

    @Delete
    suspend fun deleteForwardMessage(forwardMessage: ForwardMessage)

    @Query("SELECT * FROM messages where status = :sent_status")
    fun getForwardMessages(sent_status: Int): LiveData<List<ForwardMessage>>

    @Query("SELECT * FROM messages")
    fun getAllForwardMessages(): LiveData<List<ForwardMessage>>
}