package com.example.testapplication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = ForwardConfig::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("forwardId"),
        onDelete = ForeignKey.NO_ACTION

    )])
data class ForwardMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val forwardId: Long? = null,
    val message: String? = null,
    val fromPhone: String? = null,
    val email: String = "",
    val telegram: String = "",
    val status: Int = 0
)