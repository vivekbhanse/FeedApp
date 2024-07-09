package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvote.data.PostDetails
import com.example.myvote.data.PrimaryDetails
import com.example.myvote.domain.room.AppDatabase
import kotlinx.coroutines.launch

class FeedModel:ViewModel() {

    fun insertFeed(database: AppDatabase, p: PostDetails) = viewModelScope.launch {
        val addedID =database.PrimaryDao().insertAllPost(listOf(p))


    }
}