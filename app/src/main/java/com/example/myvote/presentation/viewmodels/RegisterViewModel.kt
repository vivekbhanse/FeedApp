package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvote.common.MyApp
import com.example.myvote.data.PrimaryDetails
import com.example.myvote.domain.room.AppDatabase
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

//    fun saveRegistration(database: AppDatabase, p: PrimaryDetails) {
//         viewModelScope.launch {
//             val addedID =database.PrimaryDao().insertAll(listOf(p))
//             addedforDB.postValue(addedID)
//         }
//
//    }

    fun insertItem(database: AppDatabase, p: PrimaryDetails) = viewModelScope.launch {
        val addedID =database.PrimaryDao().insertAll(listOf(p))


    }
}