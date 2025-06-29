package dev.prashant.planet.domain.model

data class PlanetInfoWithCharacters(
    val characters: List<Character>,
    val description: String,
    val id: Int,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)
