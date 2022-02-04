package com.wiryadev.ghiblipedia.ui.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val repository: GhibliRepository
) : ViewModel() {
    private val viewModelState = MutableStateFlow(FilmsViewModelState(isLoading = true))

    val uiState = viewModelState
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
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repository.getFilms()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(films = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + result.exception.message.toString()
                        it.copy(errorMessages = errorMessages, isLoading = false)
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
            FilmsUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            FilmsUiState.HasPosts(
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

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : FilmsUiState

    data class HasPosts(
        val films: List<Film>,
        override val isLoading: Boolean,
        override val errorMessages: List<String>,
    ) : FilmsUiState
}