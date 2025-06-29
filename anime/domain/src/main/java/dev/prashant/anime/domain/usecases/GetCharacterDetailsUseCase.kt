package dev.prashant.anime.domain.usecases

import dev.prashant.anime.domain.models.CharacterDetails
import dev.prashant.anime.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    operator fun invoke(id: Int) = flow<Result<CharacterDetails>> {
        emit(animeRepository.getCharacterDetails(id))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}