package com.example.gamegenreshub.domain.models

data class GenreDetails(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val imageBackground: String,
    val description: String,
    val isSelected: Boolean
)