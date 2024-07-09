package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myvote.data.PrimaryDetails
import com.example.myvote.domain.room.AppDatabase

class MainViewModel : ViewModel() {


    suspend fun checkUser(database: AppDatabase, usename: String, pass: String): List<PrimaryDetails> =
         database.PrimaryDao().readLoginData(usename, pass)
}



