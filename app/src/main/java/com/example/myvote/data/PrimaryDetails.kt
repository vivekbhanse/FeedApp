package com.example.myvote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "primaryDetails")
data class PrimaryDetails(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "role") var role: String="",

    )

@Entity(tableName = "postDetails")
data class PostDetails(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "post") val post: String?,
    @ColumnInfo(name = "date") val date: String?,
)