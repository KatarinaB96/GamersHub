package com.example.gamegenreshub.di

import android.content.Context
import androidx.room.Room
import com.example.gamegenreshub.data.database.GameDao
import com.example.gamegenreshub.data.database.GameHubDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGameDatabase(@ApplicationContext context: Context): GameHubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GameHubDatabase::class.java,
            "game_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideGameDao(gameDatabase: GameHubDatabase): GameDao {
        return gameDatabase.gameDao()
    }
}
