package com.example.gamegenreshub.data.network

import com.example.gamegenreshub.BuildConfig
import com.example.gamegenreshub.data.network.mapper.ApiMapper
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.models.Genres

class GenreClientImpl(
    private val networkService: NetworkService,
    private val apiMapper: ApiMapper
) : GenreClient {
    override suspend fun getGenres(): Genres {
        val result = networkService.getGenres(BuildConfig.API_KEY)
        return apiMapper.toGenres(result)
    }

    override suspend fun getGamesForGenres(genreIds: String): GamesByGenres {
        val gamesByGenresResponse = networkService.getGamesForGenres(BuildConfig.API_KEY, genreIds, 40)
        return apiMapper.toGames(gamesByGenresResponse)
    }

    override suspend fun getGameById(id: Int): Games {
        val result = networkService.getGameDetails(id, BuildConfig.API_KEY)
        return apiMapper.toGameDetails(result)
    }
}