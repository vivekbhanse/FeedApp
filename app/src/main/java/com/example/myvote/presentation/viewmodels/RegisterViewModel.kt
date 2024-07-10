package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvote.data.dto.PrimaryDetails
import com.example.myvote.data.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    fun insertItem(database: AppDatabase, p: PrimaryDetails) = viewModelScope.launch {
        val addedID = database.PrimaryDao().insertAll(listOf(p))
    }
}