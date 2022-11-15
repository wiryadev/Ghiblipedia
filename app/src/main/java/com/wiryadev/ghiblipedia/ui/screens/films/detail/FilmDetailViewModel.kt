package com.wiryadev.ghiblipedia.ui.screens.films.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.data.asResult
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FilmDetailUiState(
    val film: Film? = null,
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
)

class FilmDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: GhibliRepository,
) : ViewModel() {


    private val filmId = FilmArgs(savedStateHandle).filmId

    val uiState: StateFlow<FilmDetailUiState> = repository.getFilmDetail(filmId).asResult()
        .map { result ->
            when (result) {
                is Result.Success -> FilmDetailUiState(
                    film = result.data,
                    isLoading = false
                )

                is Result.Error -> {
                    FilmDetailUiState(
                        errorMessages = result.exception?.message,
                        isLoading = false,
                    )
                }

                Result.Loading -> FilmDetailUiState(
                    isLoading = true
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FilmDetailUiState()
        )

}