package com.wiryadev.ghiblipedia.ui.films

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import coil.compose.rememberAsyncImagePainter
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import com.wiryadev.ghiblipedia.utils.dummyFilm

@Composable
fun FilmCard(
    film: Film,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateToDetail.invoke(film.id)
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PosterImage(
                film = film,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                FilmTitle(title = film.title)
                ReleaseDateAndRunTime(film = film)
            }
        }
    }
}

@Composable
private fun PosterImage(
    film: Film,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = rememberAsyncImagePainter(model = film.image),
        contentDescription = film.title,
        modifier = modifier
            .size(
                height = 60.dp,
                width = 40.dp,
            )
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
private fun FilmTitle(
    title: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1
    )
}

@Composable
private fun ReleaseDateAndRunTime(
    film: Film
) {
    Text(
        text = stringResource(
            id = R.string.home_film_year_and_duration,
            formatArgs = arrayOf(
                film.releaseDate,
                film.runningTime,
            ),
        ),
        style = MaterialTheme.typography.body2,
    )
}

@Preview
@Composable
fun FilmCardPreview() {
    GhiblipediaTheme {
        Surface {
            FilmCard(
                film = dummyFilm,
                navigateToDetail = {},
            )
        }
    }
}