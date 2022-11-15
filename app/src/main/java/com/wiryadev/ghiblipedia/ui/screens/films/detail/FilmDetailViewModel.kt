package com.wiryadev.ghiblipedia.ui.screens.films.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.data.asResult
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FilmDetailUiState(
    val film: DetailFilm? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class FilmDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: GhibliRepository,
) : ViewModel() {


    private val filmId = FilmArgs(savedStateHandle).filmId

    val uiState: StateFlow<FilmDetailUiState> = combine(
        repository.getFilmDetail(filmId),
        repository.checkFavorite(filmId),
        ::Pair
    )
        .asResult()
        .map { detailFilmResult ->
            when (detailFilmResult) {
                is Result.Success -> FilmDetailUiState(
                    film = DetailFilm(
                        data = detailFilmResult.data.first,
                        isFavorite = detailFilmResult.data.second > 0
                    ),
                    isLoading = false
                )

                is Result.Error -> FilmDetailUiState(
                    errorMessage = detailFilmResult.exception?.message,
                    isLoading = false
                )

                is Result.Loading -> FilmDetailUiState(isLoading = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FilmDetailUiState()
        )

    fun addOrRemoveFavorite() {
        uiState.value.film?.let { detailFilm ->
            viewModelScope.launch {
                if (detailFilm.isFavorite) {
                    repository.removeFromFavorite(detailFilm.data)
                } else {
                    repository.addFavoriteFilm(detailFilm.data)
                }
            }
        }

    }

}

data class DetailFilm(
    val data: Film,
    val isFavorite: Boolean,
)