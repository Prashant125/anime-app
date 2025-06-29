package dev.prashant.planet.data.mapper

import dev.prashant.core_network.dtos.characters.CharacterDto
import dev.prashant.core_network.dtos.planets.PlanetResponse
import dev.prashant.planet.domain.model.Character
import dev.prashant.planet.domain.model.PlanetInfoWithCharacters

fun PlanetResponse.toDomainPlanetResponse(): PlanetInfoWithCharacters {
    return PlanetInfoWithCharacters(
        characters = characters.toDomainCharacter(),
        description = description,
        id = id,
        image = image,
        isDestroyed = isDestroyed,
        name = name
    )
}

fun List<CharacterDto>.toDomainCharacter(): List<Character> {
    return map {
        Character(
            affiliation = it.affiliation,
            description = it.description,
            gender = it.gender,
            id = it.id,
            image = it.image,
            ki = it.ki,
            maxKi = it.maxKi,
            name = it.name,
            race = it.race
        )
    }
}