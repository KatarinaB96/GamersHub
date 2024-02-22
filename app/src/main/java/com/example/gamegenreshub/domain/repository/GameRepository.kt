package com.example.gamegenreshub.domain.repository

import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.models.GenreDetails
import com.example.gamegenreshub.domain.models.Genres

interface GameRepository {
    suspend fun getGenres(): Genres
    suspend fun getGamesByGenreId(genreIds: List<Int>): GamesByGenres
    suspend fun getGameDetails(id: Int): Games
    suspend fun insertGenresToDatabase(genres: List<GenreDetails>)
    suspend fun getGenresFromDatabase(): List<GenreDetails>
}