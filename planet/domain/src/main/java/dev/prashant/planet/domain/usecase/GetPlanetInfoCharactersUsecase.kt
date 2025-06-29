package dev.prashant.planet.domain.usecase

import dev.prashant.planet.domain.repository.PlanetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPlanetInfoCharactersUsecase @Inject constructor(private val planetRepository: PlanetRepository) {

    operator fun invoke(id: Int) = flow {
        emit(planetRepository.getPlanetInfoWithCharacters(id))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}