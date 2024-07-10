package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myvote.data.dto.PrimaryDetails
import com.example.myvote.data.repository.MainReposiitory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainReposiitory: MainReposiitory,
) : ViewModel() {

//
//    suspend fun checkUser(
//        database: AppDatabase,
//        usename: String,
//        pass: String,
//    ): List<PrimaryDetails> = database.PrimaryDao().readLoginData(usename, pass)

    suspend fun checkUser(
        usename: String,
        pass: String,
    ): List<PrimaryDetails> = mainReposiitory.checkUser(usename, pass)
}



