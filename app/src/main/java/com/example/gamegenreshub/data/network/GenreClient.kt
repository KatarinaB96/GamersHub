package com.example.gamegenreshub.data.network


import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.models.Genres

interface GenreClient {
    suspend fun getGenres(): Genres
    suspend fun getGamesForGenres(genreIds: String): GamesByGenres
    suspend fun getGameById(id: Int): Games
}