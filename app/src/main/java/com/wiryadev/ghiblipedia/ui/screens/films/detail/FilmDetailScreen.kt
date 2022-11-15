package com.wiryadev.ghiblipedia.ui.screens.films.detail

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import com.wiryadev.ghiblipedia.utils.dummyFilm
import org.koin.androidx.compose.getViewModel

const val filmDetailRoute = "film_detail"
private const val filmIdArg = "filmId"

class FilmArgs(val filmId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(Uri.decode(savedStateHandle[filmIdArg]))
}

fun NavController.navigateToFilmDetail(filmId: String) {
    val encodedId = Uri.encode(filmId)
    this.navigate("$filmDetailRoute/$encodedId")
}

fun NavGraphBuilder.filmDetailScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        route = "$filmDetailRoute/{$filmIdArg}",
        arguments = listOf(
            navArgument(filmIdArg) { NavType.StringType }
        )
    ) {
        FilmDetailRoute(onBackPressed = onBackPressed)
    }
}

@Composable
fun FilmDetailRoute(
    onBackPressed: () -> Unit,
    viewModel: FilmDetailViewModel = getViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState by viewModel.uiState.collectAsState()

    FilmDetailScreen(
        uiState = uiState,
        onBackPressed = onBackPressed,
        onFavoriteClick = viewModel::addOrRemoveFavorite,
        scaffoldState = scaffoldState,
    )
}

@Composable
fun FilmDetailScreen(
    uiState: FilmDetailUiState,
    onBackPressed: () -> Unit,
    onFavoriteClick: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            if (uiState.film != null) {
                FloatingActionButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.semantics(mergeDescendants = true) {
                        contentDescription = "Favorite Button"
                    }
                ) {
                    Icon(
                        imageVector = if (uiState.film.isFavorite) {
                            Icons.Rounded.Favorite
                        } else {
                            Icons.Rounded.FavoriteBorder
                        },
                        contentDescription = null
                    )
                }
            }

        }
    ) { padding ->
        if (uiState.isLoading) {
            FilmDetailPlaceholder(
                onBackPressed = onBackPressed
            )
        } else {
            when {
                uiState.film != null -> {
                    FilmDetailContent(
                        detailFilm = uiState.film,
                        isLoading = false,
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
    FilmDetailContent(
        detailFilm = DetailFilm(
            data = dummyFilm,
            isFavorite = false
        ),
        isLoading = true,
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun FilmDetailContent(
    detailFilm: DetailFilm,
    isLoading: Boolean,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val film = detailFilm.data

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
        item {
            FilmMetadata(
                film = film,
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
        Column(
            modifier = Modifier.constrainAs(back) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        ) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            BackButton(onBackPressed = onBackPressed)
        }
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
            statValue = film.rating,
        )
        FilmStatBox(
            statTitle = stringResource(id = R.string.runtime),
            statValue = film.duration,
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
fun FilmMetadata(
    film: Film,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.original_title),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(
                text = " ${film.originalTitle} (${film.originalTitleRomanised})",
                style = MaterialTheme.typography.caption,
            )
        }
        Row {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.directed_by),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(
                text = " ${film.director}",
                style = MaterialTheme.typography.caption,
            )
        }
        Row {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.produced_by),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(
                text = " ${film.producer}",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
private fun FilmBannerImage(
    film: Film,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(model = film.movieBannerUrl)
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
    val painter = rememberAsyncImagePainter(model = film.imageUrl)
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
        Scaffold { padding ->
            FilmDetailContent(
                detailFilm = DetailFilm(
                    dummyFilm,
                    false
                ),
                isLoading = false,
                onBackPressed = { },
                modifier = Modifier.padding(padding)
            )
        }
    }
}