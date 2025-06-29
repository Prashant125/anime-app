package dev.prashant.anime.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.prashant.anime.domain.models.Characters
import dev.prashant.anime.domain.usecases.GetAllCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getAllCharacterUseCase: GetAllCharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow() // cannot mutate

    fun getCharcters() = getAllCharacterUseCase.invoke()
        .onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(data = data, isLoading = false, error = "") }
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message.toString()) }
            }
        }.launchIn(viewModelScope)

}



data class CharacterUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<Characters>? = null
)