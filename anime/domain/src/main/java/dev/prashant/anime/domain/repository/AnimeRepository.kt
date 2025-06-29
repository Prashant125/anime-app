package dev.prashant.anime.domain.repository

import dev.prashant.anime.domain.models.CharacterDetails
import dev.prashant.anime.domain.models.Characters

interface AnimeRepository {
    suspend fun getCharacters(): Result<List<Characters>>
    suspend fun getCharacterDetails(id: Int): Result<CharacterDetails>
}