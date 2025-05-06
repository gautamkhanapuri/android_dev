package com.example.testapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ForwardMessage::class, ForwardConfig::class], version = 1)
abstract  class ForwardDatabase: RoomDatabase() {

    abstract fun forwardConfigDao(): ForwardConfigDao

    abstract fun forwardMessageDao(): ForwardMessageDao

    companion object {

        @Volatile
        private var INSTANCE: ForwardDatabase? = null

        fun getDatabase(context: Context): ForwardDatabase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, ForwardDatabase::class.java, "forwardDB")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}