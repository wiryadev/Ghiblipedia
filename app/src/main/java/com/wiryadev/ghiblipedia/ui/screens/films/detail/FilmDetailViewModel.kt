package com.wiryadev.ghiblipedia.ui.screens.films.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FilmDetailUiState(
    val film: Film? = null,
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
)

class FilmDetailViewModel(
    private val repository: GhibliRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilmDetailUiState(isLoading = true))
    val uiState: StateFlow<FilmDetailUiState> = _uiState.asStateFlow()

//    fun showSelectedFilm(filmId: String) {
//        _uiState.update { it.copy(isLoading = true) }
//
//        viewModelScope.launch {
//            val result = repository.getFilmDetail(filmId)
//
//            _uiState.update {
//                when (result) {
//                    is Result.Success -> it.copy(
//                        film = result.data,
//                        isLoading = false
//                    )
//                    is Result.Error -> {
//                        it.copy(
//                            errorMessages = result.exception.message,
//                            isLoading = false,
//                        )
//                    }
//                }
//            }
//        }
//    }

}