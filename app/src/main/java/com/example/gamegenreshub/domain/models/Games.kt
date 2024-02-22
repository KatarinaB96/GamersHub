package com.example.gamegenreshub.domain.models

data class Games(
    val id: Int,
    val slug: String,
    val name: String,
    val description: String,
    val released: String,
    val backgroundImage: String,
    val website: String,
    val rating: Double
)
