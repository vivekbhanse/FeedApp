package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedModel @Inject constructor() : ViewModel() {

    fun insertFeed(database: AppDatabase, p: PostDetails) = viewModelScope.launch {
        val addedID = database.PrimaryDao().insertAllPost(listOf(p))


    }
}