package com.example.myvote.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.dto.PrimaryDetails

@Database(entities = [PrimaryDetails::class, PostDetails::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PrimaryDao(): PrimaryDao

}