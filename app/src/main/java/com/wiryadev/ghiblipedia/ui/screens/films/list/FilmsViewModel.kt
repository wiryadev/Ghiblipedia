package com.wiryadev.ghiblipedia.ui.screens.films.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiryadev.ghiblipedia.data.GhibliRepository
import com.wiryadev.ghiblipedia.data.Result
import com.wiryadev.ghiblipedia.data.asResult
import com.wiryadev.ghiblipedia.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val repository: GhibliRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(FilmsViewModelState())
    private val searchQuery = MutableStateFlow("")

//    val uiState: StateFlow<FilmsUiState> = viewModelState
//        .map { it.toUiState() }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Eagerly,
//            viewModelState.value.toUiState()
//        )

    val uiState: StateFlow<FilmsUiState> = combine(
        repository.getFilms(),
        searchQuery
    ) { films, query ->
        query to films.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> {
                    FilmsViewModelState(
                        films = result.data.second,
                        isLoading = false,
                        query = result.data.first
                    )
                }

                is Result.Error -> {
                    val errorMessage = result.exception?.message
                    FilmsViewModelState(
                        errorMessage = errorMessage,
                        isLoading = false,
                    )
                }

                is Result.Loading -> {
                    FilmsViewModelState(isLoading = true)
                }
            }
        }
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onSearchQueryChanged(newQuery: String) {
        Log.d("onSearchQueryChanged", newQuery)
        searchQuery.value = newQuery
    }

//    init {
//        refreshPage()
//    }

//    fun refreshPage() {
//        viewModelScope.launch {
//            repository.getFilms().asResult().collect { result ->
//                viewModelState.update {
//                    when (result) {
//                        is Result.Success -> {
//                            it.copy(films = result.data, isLoading = false)
//                        }
//
//                        is Result.Error -> {
//                            val errorMessages =
//                                it.errorMessage + result.exception?.message.toString()
//                            it.copy(errorMessages = errorMessages, isLoading = false)
//                        }
//
//                        is Result.Loading -> {
//                            it.copy(isLoading = true)
//                        }
//                    }
//                }
//            }
//        }
//    }
}

private data class FilmsViewModelState(
    val films: List<Film>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val query: String = "",
) {
    fun toUiState(): FilmsUiState {
        return if (films.isNullOrEmpty()) {
            FilmsUiState.NoData(
                isLoading = isLoading,
                errorMessage = errorMessage,
                query = query,
            )
        } else {
            FilmsUiState.HasData(
                films = films,
                isLoading = isLoading,
                errorMessage = errorMessage,
                query = query,
            )
        }
    }
}

sealed interface FilmsUiState {
    val isLoading: Boolean
    val errorMessage: String?
    val query: String

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessage: String?,
        override val query: String,
    ) : FilmsUiState

    data class HasData(
        val films: List<Film>,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        override val query: String,
    ) : FilmsUiState
}