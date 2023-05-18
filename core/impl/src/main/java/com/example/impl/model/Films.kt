package com.example.impl.model

data class Films(
    val docs: List<CurrentFilm>
)

data class CurrentFilm(
    val name: String,
    val year: Int,
    val description: String,
    val genres: List<CategoryNames>,
    val countries: List<CategoryNames>,
    val poster: Poster,
    val backdrop: Backdrop,
    val video: Video,
    val cast: Persons,
    val id: Int
)

data class CategoryNames(
    val name: String
)

data class UrlNames(
    val name: String
)

data class Poster(
    val url: String
)

data class Backdrop(
    val url : String,
    val previewUrl : String
)

data class Video(
    val trailer: Trailer
)
data class Trailer(
    val url : List<UrlNames>,
    val name : String,
    val site : String,
    val type : String,
    val size : Int
)

data class Persons(
    val id : Int,
    val photo : String,
    val name : String,
    val enName : String,
    val description : String,
    val profession : String,
    val enProfession : String
)