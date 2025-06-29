package dev.prashant.planet.domain.repository

import dev.prashant.planet.domain.model.PlanetInfoWithCharacters

interface PlanetRepository {
    suspend fun getPlanetInfoWithCharacters(id: Int): Result<PlanetInfoWithCharacters>
}