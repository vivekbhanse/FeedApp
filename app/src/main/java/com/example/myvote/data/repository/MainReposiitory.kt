package com.example.myvote.data.repository

import com.example.myvote.data.dto.PrimaryDetails
import com.example.myvote.data.room.AppDatabase
import javax.inject.Inject

class MainReposiitory @Inject constructor() {

    @Inject
    lateinit var database: AppDatabase

    suspend fun checkUser(
        usename: String,
        pass: String,
    ): List<PrimaryDetails> = database.PrimaryDao().readLoginData(usename, pass)
}