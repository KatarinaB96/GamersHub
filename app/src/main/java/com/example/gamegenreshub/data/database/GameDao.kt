package com.example.gamegenreshub.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamegenreshub.data.database.dbModels.GenreEntity

@Dao
interface GameDao {

    @Query("SELECT * FROM GenreEntity")
    suspend fun getGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: List<GenreEntity>)
}