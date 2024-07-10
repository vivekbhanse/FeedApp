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

    @Inject
    lateinit var databases: AppDatabase

    fun insertFeed(postDetails: PostDetails) = viewModelScope.launch {
        val addedID = databases.PrimaryDao().insertAllPost(listOf(postDetails))
    }

    fun updatePost(postId: String, postText: String, date: String) = viewModelScope.launch {
        databases.PrimaryDao().updatePost(postId, postText, date)
    }
}