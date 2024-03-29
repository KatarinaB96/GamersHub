package com.example.gamegenreshub.data.network.mapper

import com.example.gamegenreshub.data.network.models.GamesByGenresResponse
import com.example.gamegenreshub.data.network.models.GamesResponse
import com.example.gamegenreshub.data.network.models.GenresResponse
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Game
import com.example.gamegenreshub.domain.models.Genres
import retrofit2.Response

interface ApiMapper {
    fun toGenres(response: Response<GenresResponse>): Genres
    fun toGames(response: Response<GamesByGenresResponse>): GamesByGenres
    fun toGameDetails(response: Response<GamesResponse>): Game
}