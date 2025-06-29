package dev.prashant.planet.presentation.planetInfo

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.prashant.planet.domain.model.PlanetInfoWithCharacters
import dev.prashant.planet.domain.usecase.GetPlanetInfoCharactersUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlanetInfoViewModel @Inject constructor(
    private val getPlanetInfoCharactersUsecase: GetPlanetInfoCharactersUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanetInfoUiState())
    val uiState = _uiState.asStateFlow()

    fun getAllCharacters(id: Int) = getPlanetInfoCharactersUsecase.invoke(id)
        .onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(data = data, isLoading = false, error = "") }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message.toString()) }
            }
        }.catch { error ->
            _uiState.update { it.copy(isLoading = false, error = error.message.toString()) }
        }.launchIn(viewModelScope)

}

@Stable
data class PlanetInfoUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: PlanetInfoWithCharacters? = null
)