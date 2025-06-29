package dev.prashant.anime.domain.usecases

import dev.prashant.anime.domain.models.Characters
import dev.prashant.anime.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllCharacterUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    operator fun invoke() = flow<Result<List<Characters>>> {
        emit(animeRepository.getCharacters())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}