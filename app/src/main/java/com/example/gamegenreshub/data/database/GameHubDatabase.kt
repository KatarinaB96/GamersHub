package com.example.gamegenreshub.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamegenreshub.data.database.dbModels.GenreEntity

@Database(entities = [GenreEntity::class], version = 1)
abstract class GameHubDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
}
