package com.example.impl.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.Nullable
import androidx.room.TypeConverters
import com.example.impl.room.Converter

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "user_login") val userLogin: String,
    @ColumnInfo(name = "user_password") val userPassword: String,
    @ColumnInfo(name = "user_image") @Nullable val userImage: String? = "null",
    @ColumnInfo(name = "favorite_films")
    @TypeConverters(Converter::class) @Nullable val favoriteFilms: List<Int>? = listOf()
)
