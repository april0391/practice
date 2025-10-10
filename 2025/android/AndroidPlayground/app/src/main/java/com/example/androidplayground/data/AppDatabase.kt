package com.example.androidplayground.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: com.example.androidplayground.data.AppDatabase? = null

        fun getDatabase(context: Context): com.example.androidplayground.data.AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    com.example.androidplayground.data.AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}