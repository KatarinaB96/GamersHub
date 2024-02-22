package com.example.gamegenreshub.di

import com.example.gamegenreshub.pagination.PaginationViewModelDelegate
import com.example.gamegenreshub.pagination.PaginationViewModelDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PaginationModule {

    @Provides
    @Singleton
    fun providePaginationViewModelDelegate(): PaginationViewModelDelegate = PaginationViewModelDelegateImpl()
}