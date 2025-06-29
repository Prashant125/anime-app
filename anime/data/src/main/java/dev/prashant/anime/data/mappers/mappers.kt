package dev.prashant.anime.data.mappers

import dev.prashant.anime.domain.models.CharacterDetails
import dev.prashant.anime.domain.models.Characters
import dev.prashant.anime.domain.models.OriginPlanet
import dev.prashant.anime.domain.models.Transformation
import dev.prashant.core_network.dtos.characterDetails.CharacterDetailsResponse
import dev.prashant.core_network.dtos.characterDetails.OriginPlanetDto
import dev.prashant.core_network.dtos.characterDetails.TransformationDto
import dev.prashant.core_network.dtos.characters.CharacterDto

fun List<CharacterDto>.toDomain(): List<Characters> {
    return map {
        Characters(
            it.id,
            it.image,
            it.name
        )
    }
}

fun CharacterDetailsResponse.toCharDetailResToCharDetails():CharacterDetails {
    return CharacterDetails(
            affiliation = affiliation,
            description = description,
            gender = gender,
            id = id,
            image = image,
            ki = ki,
            maxKi = maxKi,
            name = name,
            originPlanet = originPlanet.toOrgPlanetDtoToOrgPlanet(),
            race = race,
            transformations = transformations.toTransDtoToTrans()
        )
}

fun OriginPlanetDto.toOrgPlanetDtoToOrgPlanet(): OriginPlanet {
    return OriginPlanet(
        description = this.description,
        id = id,
        image = image,
        isDestroyed = isDestroyed,
        name = name
    )
}

fun List<TransformationDto>.toTransDtoToTrans(): List<Transformation> {
    return map {
        Transformation(
            id = it.id,
            image = it.image,
            ki = it.ki,
            name = it.name
        )
    }
}