package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvote.data.PostDetails
import com.example.myvote.data.PrimaryDetails
import com.example.myvote.domain.room.AppDatabase
import kotlinx.coroutines.launch

class ShowActivityModel:ViewModel() {

    suspend fun checkPost(database: AppDatabase, usename: String): List<PostDetails> =
        database.PrimaryDao().readLoginPost(usename)

    suspend fun checkPostAll(database: AppDatabase): List<PostDetails> =
        database.PrimaryDao().readLoginPostAll()

    suspend fun deletePost(database: AppDatabase,postId:String) =
        database.PrimaryDao().deletePost2(postId)
}