package com.example.impl.rest

import com.example.impl.model.Films
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IFilmsApi {

    @GET("v1.3/movie")
    suspend fun getFilmByGenre(@Query("genres.name") genre: String): Response<Films>

    @GET("v1.3/movie?selectFields=id&selectFields=name&selectFields=description&selectFields=poster.url&selectFields=backdrop.url&selectFields=videos.trailers.url&selectFields=videos.trailers.site&selectFields=year&selectFields=countries.name&selectFields=genres.name&selectFields=backdrop.url&selectFields=persons.photo&selectFields=persons.name&page=1&videos.trailers.site=youtube")
    suspend fun getFilmById(
        @Query("id") id: Int,
        @Query("selectFields") filmId: String? = "id",
        @Query("selectFields") name: String? = "name",
        @Query("selectFields") year: String? = "year",
        @Query("selectFields") description: String? = "description",
        @Query("selectFields") poster: String? = "poster.url",
        @Query("selectFields") backdrop: String? = "backdrop.url",
        @Query("selectFields") videosUrl: String? = "videos.trailers.url",
        @Query("selectFields") videosSite: String? = "videos.trailers.site",
        @Query("selectFields") countries: String? = "countries.name",
        @Query("selectFields") genres: String? = "genres.name",
        @Query("selectFields") personsPhoto: String? = "persons.photo",
        @Query("selectFields") personName: String? = "persons.name",
        @Query("page") page: String? = "1",
        @Query("videos.trailers.site") site: String? = "youtube"
    ): Response<Films>

    @GET("v1.3/movie")
    suspend fun searchFilm(@Query("name") name: String) : Response<Films>

    @GET("v1.3/movie")
    suspend fun getBestFilms(
        @Query("selectFields") id: String? = "id",
        @Query("selectFields") name: String? = "name",
        @Query("selectFields") description: String? = "description",
        @Query("selectFields") backdrop: String? = "backdrop.url",
        @Query("selectFields") videos: String? = "videos.trailers.url",
        @Query("page") page: String? = "1",
        @Query("limit") limit: String? = "10",
        @Query("videos.trailers.site") site: String? = "youtube"
    ): Response<Films>
}