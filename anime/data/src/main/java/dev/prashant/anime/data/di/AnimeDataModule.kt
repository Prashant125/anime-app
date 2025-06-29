package dev.prashant.anime.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.prashant.anime.data.repository.AnimeRepositoryImpl
import dev.prashant.anime.domain.repository.AnimeRepository
import dev.prashant.core_network.service.ApiService

@InstallIn(SingletonComponent::class)
@Module
object AnimeDataModule {

    @Provides
    fun provideAnimeRepository(apiService: ApiService): AnimeRepository {
        return AnimeRepositoryImpl(apiService)
    }
}