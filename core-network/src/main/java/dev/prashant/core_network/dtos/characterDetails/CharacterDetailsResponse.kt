package dev.prashant.core_network.dtos.characterDetails

data class CharacterDetailsResponse(
    val affiliation: String,
    val deletedAt: Any,
    val description: String,
    val gender: String,
    val id: Int,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val originPlanet: OriginPlanetDto,
    val race: String,
    val transformations: List<TransformationDto>
)