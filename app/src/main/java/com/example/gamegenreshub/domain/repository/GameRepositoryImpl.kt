package com.example.gamegenreshub.domain.repository

import com.example.gamegenreshub.data.network.GenreClient
import com.example.gamegenreshub.data.database.GameDao
import com.example.gamegenreshub.data.database.dbModels.GenreEntity
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Game
import com.example.gamegenreshub.domain.models.GenreDetails
import com.example.gamegenreshub.domain.models.Genres
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val genreClient: GenreClient,
    private val gameDao: GameDao
) : GameRepository {
    override suspend fun getGenres(): Genres {
        return genreClient.getGenres()
    }

    override suspend fun getGenresFromDatabase(): List<GenreDetails> {
        val genres = gameDao.getGenres().map {
            GenreDetails(
                it.id,
                it.name,
                it.slug,
                it.gamesCount,
                it.imageBackground,
                it.description,
                it.isSelected
            )
        }.filter { it.isSelected }

        return genres.ifEmpty { getGenres().results }
    }

    override suspend fun getGamesByGenreId(genreIds: List<Int>, pageNumber: Int): GamesByGenres {
        return genreClient.getGamesForGenres(genreIds.joinToString(","), pageNumber)
    }

    override suspend fun getGameDetails(id: Int): Game {
        return genreClient.getGameById(id)
    }

    override suspend fun insertGenresToDatabase(genres: List<GenreDetails>) {
        val genreEntities = genres.map { GenreEntity(it.id, it.name, it.slug, it.gamesCount, it.imageBackground, it.description, it.isSelected) }

        gameDao.insertGenre(genreEntities)
    }

}