package com.example.impl.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.impl.room.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM users_table WHERE user_login = :login AND user_password = :password")
    fun checkLogIn(login: String, password: String): User?

    /*@Query("SELECT favorite_films FROM users_table WHERE id = :userId")
    fun getFavoriteFilms(userId: Int): List<Int>?

    @Transaction
    @Query("UPDATE users_table SET favorite_films = :updatedFavoriteFilms WHERE id = :userId")
    fun updateFavoriteFilms(userId: Int, updatedFavoriteFilms: List<Int>): Int

    @Transaction
    fun addFavoriteFilm(userId: Int, number: Int) {
        val currentFavoriteFilms = getFavoriteFilms(userId)
        val updatedFavoriteFilms = currentFavoriteFilms?.toMutableList()
        updatedFavoriteFilms?.add(number)
        updatedFavoriteFilms?.let { updateFavoriteFilms(userId, it) }
    }*/
}