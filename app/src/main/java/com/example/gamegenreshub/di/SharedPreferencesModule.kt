package com.example.gamegenreshub.di

import android.content.Context
import com.example.gamegenreshub.data.preferences.DeviceSharedPreferences
import com.example.gamegenreshub.data.preferences.DeviceSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): DeviceSharedPreferences {
        return DeviceSharedPreferencesImpl(context)
    }
}