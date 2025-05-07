package com.example.testapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Forwards::class, SentMessages::class], version = 3)
abstract class TestAppDatabase : RoomDatabase() {

    abstract fun forwardDao(): ForwardDao
    abstract fun sentMessagesDao(): SentMessagesDao

}