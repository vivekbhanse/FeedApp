package com.example.myvote.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.myvote.common.utils
import com.example.myvote.data.room.AppDatabase
import com.example.myvote.data.room.PrimaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun providePrimaryDao(appDatabase: AppDatabase): PrimaryDao {
        return appDatabase.PrimaryDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(utils.sharedPrefFile, Context.MODE_PRIVATE)
    }
}