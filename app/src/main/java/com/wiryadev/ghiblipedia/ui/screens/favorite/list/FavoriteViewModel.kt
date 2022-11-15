package com.wiryadev.ghiblipedia.ui.screens.favorite.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.data.asResult
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(
    repository: GhibliRepository
) : ViewModel() {

    val uiState: StateFlow<FavoriteUiState> = repository.getFavoriteFilms()
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> {
                    FavoriteViewModelState(films = result.data, isLoading = false)
                }

                is Result.Error -> {
                    val errorMessages = result.exception?.message.toString()
                    FavoriteViewModelState(errorMessage = errorMessages, isLoading = false)
                }

                is Result.Loading -> {
                    FavoriteViewModelState(isLoading = true)
                }
            }
        }
        .map(FavoriteViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = FavoriteViewModelState().toUiState()
        )
}

private data class FavoriteViewModelState(
    val films: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    fun toUiState(): FavoriteUiState {
        return if (films.isEmpty()) {
            FavoriteUiState.NoData(
                isLoading = isLoading,
                errorMessage = errorMessage,
            )
        } else {
            FavoriteUiState.HasData(
                films = films,
                isLoading = isLoading,
                errorMessage = errorMessage,
            )
        }
    }
}

sealed interface FavoriteUiState {
    val isLoading: Boolean
    val errorMessage: String?

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessage: String?,
    ) : FavoriteUiState

    data class HasData(
        val films: List<Film>,
        override val isLoading: Boolean,
        override val errorMessage: String?,
    ) : FavoriteUiState
}