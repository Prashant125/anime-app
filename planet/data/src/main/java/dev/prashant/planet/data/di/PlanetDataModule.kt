package dev.prashant.planet.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.prashant.core_network.service.ApiService
import dev.prashant.planet.data.repository.PlanetRepositoryImpl
import dev.prashant.planet.domain.repository.PlanetRepository

@InstallIn(SingletonComponent::class)
@Module
object PlanetDataModule {

    @Provides
    fun providePlanetRespository(apiService: ApiService): PlanetRepository {
        return PlanetRepositoryImpl(apiService)
    }

}