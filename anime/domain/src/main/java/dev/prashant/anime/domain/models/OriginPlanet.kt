package dev.prashant.anime.domain.models

data class OriginPlanet(
    val description: String,
    val id: Int,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)