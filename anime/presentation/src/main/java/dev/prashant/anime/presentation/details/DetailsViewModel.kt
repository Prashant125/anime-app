package dev.prashant.anime.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.prashant.anime.domain.models.CharacterDetails
import dev.prashant.anime.domain.usecases.GetCharacterDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetCharacterDetailsUseCase
)  :ViewModel() {

    private val _uiState = MutableStateFlow(DetailsuiState())
    val uiState = _uiState.asStateFlow()

    fun getDetails(id: Int) = getUserDetailsUseCase.invoke(id)
        .onStart { _uiState.update { it.copy(isLoading = true) } }
        .onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(data = data, isLoading = false, error = "") }
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message.toString()) }
            }
        }.catch { e->
            _uiState.update { it.copy(error = e.message.toString()) }
        }
        .launchIn(viewModelScope)
}


data class DetailsuiState(
    val isLoading: Boolean = false,
    val error: String ="",
    val data: CharacterDetails? = null
)