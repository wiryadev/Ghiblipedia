package com.wiryadev.ghiblipedia.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wiryadev.ghiblipedia.model.Film


@Composable
fun FilmList(
    films: List<Film>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 0.dp,
            bottom = BottomNavigationHeight
                    + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
            start = 16.dp,
            end = 16.dp,
        ),
        state = state,
        modifier = modifier,
    ) {
        filmCardItems(
            items = films,
            filmMapper = { it },
            onItemClick = navigateToDetail,
        )
    }
}

fun <T> LazyListScope.filmCardItems(
    items: List<T>,
    filmMapper: (item: T) -> Film,
    onItemClick: (String) -> Unit,
    itemModifier: Modifier = Modifier
) = items(
    items = items,
    key = { filmMapper(it).id },
    itemContent = { item ->
        val film = filmMapper(item)

        FilmCard(
            filmId = film.id,
            title = film.title,
            imageUrl = film.posterUrl,
            releaseDate = film.releaseDate,
            duration = film.duration,
            onItemClick = onItemClick,
            modifier = itemModifier.padding(vertical = 8.dp),
        )
    }
)