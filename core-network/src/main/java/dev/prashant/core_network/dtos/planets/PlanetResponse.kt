package dev.prashant.core_network.dtos.planets

import dev.prashant.core_network.dtos.characters.CharacterDto

data class PlanetResponse(
    val characters: List<CharacterDto>,
    val deletedAt: Any,
    val description: String,
    val id: Int,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)