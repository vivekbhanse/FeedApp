package com.example.myvote.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.dto.PrimaryDetails

@Dao
interface PrimaryDao {

    @Query("SELECT * FROM primaryDetails")
    suspend fun getAll(): List<PrimaryDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Courses: List<PrimaryDetails>)

    @Delete
    suspend fun delete(Course: PrimaryDetails)

    @Query("SELECT * FROM primaryDetails WHERE username LIKE:usernames AND password LIKE:passwordss")
    suspend fun readLoginData(usernames: String, passwordss: String): List<PrimaryDetails>

    @Query("SELECT * FROM postDetails WHERE username LIKE:usernames ")
    suspend fun readLoginPost(usernames: String): List<PostDetails>

    @Query("SELECT * FROM postDetails ")
    suspend fun readLoginPostAll(): List<PostDetails>

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertAllPost(post: List<PostDetails>)

    @Query("delete from postDetails WHERE id=:id")
    suspend fun deletePost2(id: String)

    @Query("UPDATE postDetails SET post=:postText,date=:date WHERE id = :id")
    suspend fun updatePost(id: String ,postText: String?, date: String)

    @Delete
    suspend fun deletePost(post: PostDetails)
}