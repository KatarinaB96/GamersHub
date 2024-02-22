package com.example.gamegenreshub.data.network.mapper

import com.example.gamegenreshub.data.network.models.GamesByGenresResponse
import com.example.gamegenreshub.data.network.models.GamesResponse
import com.example.gamegenreshub.data.network.models.GenreDetailsResponse
import com.example.gamegenreshub.data.network.models.GenresResponse
import com.example.gamegenreshub.domain.models.GamesByGenres
import com.example.gamegenreshub.domain.models.Game
import com.example.gamegenreshub.domain.models.GenreDetails
import com.example.gamegenreshub.domain.models.Genres
import retrofit2.Response

class ApiMapperImpl : ApiMapper {

    override fun toGenres(response: GenresResponse): Genres {
        return Genres(response.count, response.next, response.previous, toGenreDetails(response.results))
    }

    override fun toGames(response: Response<GamesByGenresResponse>): GamesByGenres {
        if (response.isSuccessful) {
            val body = response.body()
            return GamesByGenres(body?.next.orEmpty(), toGameDetailsScreenModel(body?.results.orEmpty()))
        } else {
            throw Exception(response.message())
        }
    }

    override fun toGameDetails(response: Response<GamesResponse>): Game {
        if (response.isSuccessful) {
            val body = response.body()

            return Game(
                body?.id ?: 0,
                body?.slug.orEmpty(),
                body?.name.orEmpty(),
                body?.description.orEmpty(),
                body?.released.orEmpty(),
                body?.backgroundImage.orEmpty(),
                body?.website.orEmpty(),
                body?.rating ?: 0.0
            )
        } else {
            throw Exception(response.message())
        }
    }

    private fun toGameDetailsScreenModel(gamesResponse: List<GamesResponse>): List<Game> {
        return gamesResponse.map { game ->
            Game(
                game.id,
                game.name,
                game.slug,
                game.description ?: "",
                game.released,
                game.backgroundImage ?: "",
                game.website ?: "",
                game.rating ?: 0.0
            )

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
        }
    }
}