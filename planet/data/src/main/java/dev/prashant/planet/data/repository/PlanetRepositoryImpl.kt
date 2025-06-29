package dev.prashant.planet.data.repository

import dev.prashant.core_network.service.ApiService
import dev.prashant.planet.data.mapper.toDomainPlanetResponse
import dev.prashant.planet.domain.model.PlanetInfoWithCharacters
import dev.prashant.planet.domain.repository.PlanetRepository

class PlanetRepositoryImpl(
    private val apiService: ApiService
): PlanetRepository {
    override suspend fun getPlanetInfoWithCharacters(id: Int): Result<PlanetInfoWithCharacters> {
        return try {
            val response = apiService.getPlanetCharacters(id)
            Result.success(response.toDomainPlanetResponse())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}