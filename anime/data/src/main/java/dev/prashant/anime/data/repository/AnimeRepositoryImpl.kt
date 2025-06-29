package dev.prashant.anime.data.repository

import dev.prashant.anime.data.mappers.toCharDetailResToCharDetails
import dev.prashant.anime.data.mappers.toDomain
import dev.prashant.anime.domain.models.CharacterDetails
import dev.prashant.anime.domain.models.Characters
import dev.prashant.anime.domain.repository.AnimeRepository
import dev.prashant.core_network.service.ApiService

class AnimeRepositoryImpl(private val apiService: ApiService) :
    AnimeRepository {
    override suspend fun getCharacters(): Result<List<Characters>> {
        return try {
            val result = apiService.getAllCharacters(limit = 60)
            val response = result.items.toDomain()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharacterDetails(id: Int): Result<CharacterDetails> {
        return try {
            val result = apiService.getCharacterDetails(id)
            val response = result.toCharDetailResToCharDetails()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}