package com.example.testapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Forwards::class], version = 1)
abstract class TestAppDatabase : RoomDatabase() {

    abstract fun forwardDao(): ForwardDao

}