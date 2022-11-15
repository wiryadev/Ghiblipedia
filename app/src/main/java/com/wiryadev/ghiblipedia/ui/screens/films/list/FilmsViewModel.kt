package com.wiryadev.ghiblipedia.ui.screens.films.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.data.asResult
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val repository: GhibliRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(FilmsViewModelState())

    val uiState: StateFlow<FilmsUiState> = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshPage()
    }

    fun refreshPage() {
        viewModelScope.launch {
            repository.getFilms().asResult().collect { result ->
                viewModelState.update {
                    when (result) {
                        is Result.Success -> {
                            it.copy(films = result.data, isLoading = false)
                        }
                        is Result.Error -> {
                            val errorMessages =
                                it.errorMessages + result.exception?.message.toString()
                            it.copy(errorMessages = errorMessages, isLoading = false)
                        }

                        is Result.Loading -> {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}

private data class FilmsViewModelState(
    val films: List<Film>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList(),
) {
    fun toUiState(): FilmsUiState {
        return if (films.isNullOrEmpty()) {
            FilmsUiState.NoData(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            FilmsUiState.HasData(
                films = films,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
    }
}

sealed interface FilmsUiState {
    val isLoading: Boolean
    val errorMessages: List<String>

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : FilmsUiState

    data class HasData(
        val films: List<Film>,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : FilmsUiState
}