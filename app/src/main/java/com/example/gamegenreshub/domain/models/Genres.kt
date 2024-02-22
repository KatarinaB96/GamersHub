package com.example.gamegenreshub.domain.models

data class Genres(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GenreDetails>
)
