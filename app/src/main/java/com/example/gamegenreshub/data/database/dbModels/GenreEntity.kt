package com.example.gamegenreshub.data.database.dbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "games_count") val gamesCount: Int,
    @ColumnInfo(name = "image_background") val imageBackground: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean,
)

