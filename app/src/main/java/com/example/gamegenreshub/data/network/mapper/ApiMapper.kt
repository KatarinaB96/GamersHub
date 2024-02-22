package com.example.gamegenreshub.data.network.mapper

import com.example.gamegenreshub.data.network.models.GamesByGenresResponse
import com.example.gamegenreshub.data.network.models.GamesResponse
import com.example.gamegenreshub.data.network.models.GenresResponse
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.models.Genres

interface ApiMapper {
    fun toGenres(response: GenresResponse): Genres
    fun toGames(response: GamesByGenresResponse): GamesByGenres
    fun toGameDetails(gamesResponse: GamesResponse): Games
}