package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myvote.data.dto.PrimaryDetails
import com.example.myvote.data.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


    suspend fun checkUser(
        database: AppDatabase,
        usename: String,
        pass: String
    ): List<PrimaryDetails> = database.PrimaryDao().readLoginData(usename, pass)
}



