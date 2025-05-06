package com.example.testapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Forwards::class, ForwardMessage::class], version = 1)
abstract class TestAppDatabase : RoomDatabase() {

    abstract fun forwardDao(): ForwardDao

    abstract fun forwardMessageDao(): ForwardMessageDao

    companion object {

        @Volatile
        private var INSTANCE: TestAppDatabase? = null

        fun getDatabase(context: Context): TestAppDatabase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, TestAppDatabase::class.java, "forwardDB")
                        .build()
                }

            }
            return INSTANCE!!
        }
    }

}