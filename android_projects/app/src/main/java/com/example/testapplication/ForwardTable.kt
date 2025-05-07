package com.example.testapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "forwards")
data class Forwards(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val message: String,
    val fromPhone: String = "",
    val email: String,
    val telegram: String,
    val isActive: Boolean,
    val numberOfMessagesForwarded: Int
)

@Dao
interface ForwardDao {
    @Insert
    suspend fun insertForward(fwd: Forwards)

    @Update
    suspend fun updateForward(fwd: Forwards)

    @Delete
    suspend fun deleteMessage(fwd: Forwards)

    @Query("SELECT * FROM forwards")
    fun getAllMessages(): LiveData<List<Forwards>>

    @Query("SELECT * FROM forwards")
    suspend fun getForwards(): List<Forwards>
}