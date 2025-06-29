package dev.prashant.core_network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.prashant.core_network.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}