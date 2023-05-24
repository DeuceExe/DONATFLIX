package com.example.api

interface IUserInfo {
    fun onUserDataReceived(login: String, image: String, favoriteFilms: List<Int>)
}