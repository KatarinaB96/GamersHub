package com.example.gamegenreshub.di

import com.example.gamegenreshub.BuildConfig
import com.example.gamegenreshub.data.network.GenreClient
import com.example.gamegenreshub.data.network.GenreClientImpl
import com.example.gamegenreshub.data.network.NetworkService
import com.example.gamegenreshub.data.network.mapper.ApiMapper
import com.example.gamegenreshub.data.network.mapper.ApiMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val TIMEOUT_SECONDS = 30L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGameService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiMapper(): ApiMapper = ApiMapperImpl()

    @Provides
    @Singleton
    fun provideClient(apiMapper: ApiMapper, networkService: NetworkService): GenreClient {
        return GenreClientImpl(networkService, apiMapper)
    }

}