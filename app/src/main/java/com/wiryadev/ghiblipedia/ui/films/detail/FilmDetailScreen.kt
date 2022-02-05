package com.wiryadev.ghiblipedia.ui.films.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import com.wiryadev.ghiblipedia.utils.dummyFilm

@Composable
fun FilmDetailScreen(
    uiState: FilmDetailUiState,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (uiState.isLoading) {
            FilmDetailPlaceholder(
                onBackPressed = onBackPressed
            )
        } else {
            when {
                uiState.film != null -> {
                    FilmDetailContent(
                        film = uiState.film,
                        isLoading = uiState.isLoading,
                        onBackPressed = onBackPressed
                    )
                }
                uiState.errorMessages != null -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            text = uiState.errorMessages,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilmDetailPlaceholder(
    onBackPressed: () -> Unit,
) {
    FilmDetailContent(film = dummyFilm, isLoading = true, onBackPressed = onBackPressed)
}

@Composable
private fun FilmDetailContent(
    film: Film,
    isLoading: Boolean,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        item {
            FilmDetailHeader(
                film = film,
                onBackPressed = onBackPressed,
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade(),
                )
            )
        }
        item {
            FilmDetailStat(
                film = film,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.fade(),
                    )
            )
        }
        item {
            FilmDescription(
                description = film.description,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.fade(),
                    )
            )
        }
    }
}

@Composable
fun FilmDetailHeader(
    film: Film,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (banner, poster, title, back) = createRefs()

        FilmBannerImage(
            film = film,
            modifier = Modifier.constrainAs(banner) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        BackButton(
            onBackPressed = onBackPressed,
            modifier = Modifier.constrainAs(back) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        FilmPosterImage(
            film = film,
            modifier = Modifier.constrainAs(poster) {
                top.linkTo(banner.bottom)
                bottom.linkTo(banner.bottom)
                start.linkTo(parent.start, margin = 16.dp)
            }
        )
        FilmTitle(
            title = film.title,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(banner.bottom, margin = 8.dp)
                start.linkTo(poster.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
fun BackButton(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onBackPressed,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            tint = MaterialTheme.colors.background,
        )
    }
}

@Composable
fun FilmDetailStat(
    film: Film,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        FilmStatBox(
            statTitle = stringResource(id = R.string.release),
            statValue = film.releaseDate,
        )
        FilmStatBox(
            statTitle = stringResource(id = R.string.rating),
            statValue = film.rtScore,
        )
        FilmStatBox(
            statTitle = stringResource(id = R.string.runtime),
            statValue = film.runningTime,
        )
    }
}

@Composable
fun FilmStatBox(
    statTitle: String,
    statValue: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(all = 8.dp),
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = statTitle,
                style = MaterialTheme.typography.caption
            )
        }
        Text(
            text = statValue,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun FilmDescription(
    description: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = description,
        style = MaterialTheme.typography.body2.copy(
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
        ),
        modifier = modifier,
    )
}

@Composable
private fun FilmBannerImage(
    film: Film,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(model = film.movieBanner)
    val isPainterLoading = painter.state is AsyncImagePainter.State.Loading

    Image(
        painter = painter,
        contentDescription = film.title,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp)
            .alpha(.75f)
            .placeholder(
                visible = isPainterLoading,
                highlight = PlaceholderHighlight.fade()
            )
    )
}

@Composable
private fun FilmPosterImage(
    film: Film,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(model = film.image)
    val isPainterLoading = painter.state is AsyncImagePainter.State.Loading

    Image(
        painter = painter,
        contentDescription = film.title,
        modifier = modifier
            .size(
                height = 150.dp,
                width = 100.dp,
            )
            .clip(MaterialTheme.shapes.small)
            .placeholder(
                visible = isPainterLoading,
                highlight = PlaceholderHighlight.fade()
            )
    )
}

@Composable
private fun FilmTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h4,
        modifier = modifier,
    )
}

@Preview
@Composable
fun DetailScreenPreview() {
    GhiblipediaTheme {
        Scaffold {
            FilmDetailContent(film = dummyFilm, isLoading = false, onBackPressed = { })
        }
    }
}