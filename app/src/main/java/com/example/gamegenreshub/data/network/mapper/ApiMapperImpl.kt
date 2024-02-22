package com.example.gamegenreshub.data.network.mapper

import com.example.gamegenreshub.data.network.models.GamesByGenresResponse
import com.example.gamegenreshub.data.network.models.GamesResponse
import com.example.gamegenreshub.data.network.models.GenreDetailsResponse
import com.example.gamegenreshub.data.network.models.GenresResponse
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Games
import com.example.gamegenreshub.domain.models.GenreDetails
import com.example.gamegenreshub.domain.models.Genres

class ApiMapperImpl : ApiMapper {

    override fun toGenres(response: GenresResponse): Genres {
        return Genres(response.count, response.next, response.previous, toGenreDetails(response.results))
    }

    override fun toGames(response: GamesByGenresResponse): GamesByGenres {
        return GamesByGenres(response.next, toGameDetailsScreenModel(response.results))
    }

    override fun toGameDetails(gamesResponse: GamesResponse): Games {
        return Games(
            gamesResponse.id,
            gamesResponse.slug,
            gamesResponse.name,
            gamesResponse.description.orEmpty(),
            gamesResponse.released,
            gamesResponse.backgroundImage ?: "",
            gamesResponse.website ?: "",
            gamesResponse.rating ?: 0.0
        )
    }

    private fun toGameDetailsScreenModel(gamesResponse: List<GamesResponse>): List<Games> {
        return gamesResponse.map { game ->
            Games(
                game.id,
                game.name,
                game.slug,
                game.description ?: "",
                game.released,
                game.backgroundImage ?: "",
                game.website ?: "",
                game.rating ?: 0.0
            )
            //                game.games.map { game -> GamesScreenModel(game.id, game.slug, game.name, game.added) })
        }
    }

    private fun toGenreDetails(genreDetailsResponse: List<GenreDetailsResponse>): List<GenreDetails> {
        return genreDetailsResponse.map { genre ->
            GenreDetails(
                genre.id,
                genre.name,
                genre.slug,
                genre.gamesCount,
                genre.imageBackground ?: "",
                genre.description ?: "",
                false
            )
            //                genre.games.map { game -> GamesScreenModel(game.id, game.slug, game.name, game.added) })
        }
    }
}