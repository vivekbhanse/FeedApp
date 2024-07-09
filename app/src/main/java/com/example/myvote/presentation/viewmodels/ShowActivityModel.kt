package com.example.myvote.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowActivityModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var databases: AppDatabase

    suspend fun checkPost(database: AppDatabase, usename: String): List<PostDetails> =
        database.PrimaryDao().readLoginPost(usename)

    suspend fun checkPostAll(database: AppDatabase): List<PostDetails> =
        database.PrimaryDao().readLoginPostAll()

    suspend fun deletePost(database: AppDatabase, postId: String) =
        database.PrimaryDao().deletePost2(postId)

    suspend fun updatePost(postId: String, postText: String, date: String) =
        databases.PrimaryDao().updatePost(postId,postText,date)
}