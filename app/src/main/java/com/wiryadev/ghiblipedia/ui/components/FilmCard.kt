package com.wiryadev.ghiblipedia.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import com.wiryadev.ghiblipedia.utils.dummyFilm


@Composable
fun FilmCard(
    filmId: String,
    title: String,
    imageUrl: String,
    releaseDate: String,
    duration: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onItemClick(filmId) }
                .padding(8.dp),
        ) {
            PosterImage(
                title = title,
                imageUrl = imageUrl,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                FilmTitle(title = title)
                ReleaseDateAndRunTime(
                    releaseDate = releaseDate,
                    duration = duration,
                )
            }
        }
    }
}

@Composable
private fun PosterImage(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = title,
        modifier = modifier
            .size(
                height = 90.dp,
                width = 60.dp,
            )
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
private fun FilmTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
        modifier = modifier,
    )
}

@Composable
private fun ReleaseDateAndRunTime(
    releaseDate: String,
    duration: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(
            id = R.string.home_film_year_and_duration,
            formatArgs = arrayOf(
                releaseDate,
                duration,
            ),
        ),
        style = MaterialTheme.typography.body2,
        modifier = modifier,
    )
}

@Preview
@Composable
fun FilmCardPreview() {
    GhiblipediaTheme {
        Surface {
            FilmCard(
                filmId = dummyFilm.id,
                title = dummyFilm.title,
                imageUrl = dummyFilm.posterUrl,
                releaseDate = dummyFilm.releaseDate,
                duration = dummyFilm.duration,
                onItemClick = {},
            )
        }
    }
}