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
    val id: Int
)

data class CategoryNames(
    val name: String
)

data class Poster(
    val url: String
)