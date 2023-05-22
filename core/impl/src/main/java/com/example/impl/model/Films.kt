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
    val videos: Videos,
    val persons: List<Persons>,
    val id: Int
)

data class CategoryNames(
    val name: String
)

data class Poster(
    val url: String
)

data class Backdrop(
    val url : String,
    val previewUrl : String
)

data class Videos(
    val trailers: List<Trailers>
)
data class Trailers(
    val url : String
)

data class Persons(
    val photo : String,
    val name : String
)