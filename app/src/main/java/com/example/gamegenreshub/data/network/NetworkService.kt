package com.example.gamegenreshub.data.network

import com.example.gamegenreshub.data.network.models.GamesByGenresResponse
import com.example.gamegenreshub.data.network.models.GamesResponse
import com.example.gamegenreshub.data.network.models.GenresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("genres")
    suspend fun getGenres(@Query("key") apiKey: String): Response<GenresResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Response<GamesResponse>

    @GET("games")
    suspend fun getGamesForGenres(
        @Query("key") apiKey: String,
        @Query("genres") genreIds: String,
        @Query("page_size") pageSize: Int,
        @Query("page") pageNumber: Int
    ): Response<GamesByGenresResponse>
}