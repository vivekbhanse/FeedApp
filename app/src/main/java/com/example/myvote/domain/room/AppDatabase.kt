package com.example.myvote.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myvote.data.PostDetails
import com.example.myvote.data.PrimaryDetails

@Database(entities = [PrimaryDetails::class,PostDetails::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PrimaryDao(): PrimaryDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .build()
    }
}