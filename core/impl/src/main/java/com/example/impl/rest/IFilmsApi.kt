package com.example.impl.rest

import com.example.impl.model.Films
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IFilmsApi {

    @GET("v1/movie")
    suspend fun getFilmByGenre(@Query("genres.name") genre: String): Response<Films>

    @GET("v1/movie")
    suspend fun getFilmById(@Query("id") id: Int): Response<Films>
}