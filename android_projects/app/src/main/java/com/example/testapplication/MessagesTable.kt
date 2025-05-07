package com.example.testapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(
    tableName = "sentMessages",
    foreignKeys = [
        ForeignKey(
            entity = Forwards::class,
            parentColumns = ["id"],
            childColumns = ["configId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
    )
data class SentMessages(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int = 0,
    val configId: Int, // this is the foreign key TODO(fill the annotator)
    val entireMessage: String,
    val receivedFrom: String,
    val receivedAt: String,  // format: dd-MM-yy HH:mm America/New_York
    val sentTo: String,
    val sentAt: String,  // format: dd-MM-yy HH:mm America/New_York
    val sentMode: String  // Either Email or Telegram for now.
)

@Dao
interface SentMessagesDao {
    @Insert
    suspend fun insertSentMessage(sentMessage: SentMessages)

    @Query("SELECT * FROM sentMessages WHERE configId = :configId")
    fun getMessagesForConfig(configId: Int): LiveData<List<SentMessages>>

    @Query("SELECT COUNT(*) FROM sentMessages WHERE configId = :configId")
    fun getCountForConfig(configId: Int): LiveData<Int>
}