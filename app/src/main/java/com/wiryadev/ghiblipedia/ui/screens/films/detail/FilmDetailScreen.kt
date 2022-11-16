package com.wiryadev.ghiblipedia.ui.screens.films.detail

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.ui.components.EmptyContent
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import com.wiryadev.ghiblipedia.utils.dummyFilm
import org.koin.androidx.compose.koinViewModel

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
        arguments = listOf(navArgument(filmIdArg) { NavType.StringType })
    ) {
        FilmDetailRoute(onBackPressed = onBackPressed)
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FilmDetailRoute(
    onBackPressed: () -> Unit,
    viewModel: FilmDetailViewModel = koinViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        modifier = modifier,
        floatingActionButton = {
            if (uiState.film != null) {
                FloatingActionButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .semantics(mergeDescendants = true) {
                            contentDescription = "Favorite Button"
                        },
                ) {
                    Icon(
                        imageVector = if (uiState.film.isFavorite) {
                            Icons.Rounded.Favorite
                        } else {
                            Icons.Rounded.FavoriteBorder
                        },
                        contentDescription = null,
                    )
                }
            }
        },
    ) { padding ->
        if (uiState.isLoading) {
            FilmDetailPlaceholder(
                onBackPressed = onBackPressed
            )
        } else {
            when {
                uiState.film != null -> {
                    FilmDetailContent(
                        film = uiState.film.data,
                        isLoading = false,
                        onBackPressed = onBackPressed,
                        modifier = Modifier.padding(padding),
                    )
                }

                uiState.errorMessage != null -> {
                    EmptyContent(
                        message = uiState.errorMessage,
                        illustration = R.drawable.ic_not_found,
                    )
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
        film = dummyFilm,
        isLoading = true,
        onBackPressed = onBackPressed,
    )
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
                title = film.title,
                imageUrl = film.posterUrl,
                bannerUrl = film.bannerUrl,
                onBackPressed = onBackPressed,
                modifier = Modifier.placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade(),
                ),
            )
        }
        item {
            FilmDetailStat(
                releaseDate = film.releaseYear,
                rating = film.rating,
                duration = film.duration,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.fade(),
                    ),
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
                originalTitle = film.originalTitle,
                originalTitleRomanised = film.originalTitleRomanised,
                director = film.director,
                producer = film.producer,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.fade(),
                    ),
            )
        }
        item {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
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
    title: String,
    imageUrl: String,
    bannerUrl: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (bannerSection, posterSection, titleSection, backNavigation) = createRefs()

        FilmBannerImage(imageUrl = bannerUrl, modifier = Modifier.constrainAs(bannerSection) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Column(modifier = Modifier.constrainAs(backNavigation) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }) {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            BackButton(onBackPressed = onBackPressed)
        }
        FilmPosterImage(imageUrl = imageUrl, modifier = Modifier.constrainAs(posterSection) {
            top.linkTo(bannerSection.bottom)
            bottom.linkTo(bannerSection.bottom)
            start.linkTo(parent.start, margin = 16.dp)
        })
        FilmTitle(title = title, modifier = Modifier.constrainAs(titleSection) {
            top.linkTo(bannerSection.bottom, margin = 8.dp)
            start.linkTo(posterSection.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        })
    }
}

@Composable
fun FilmDetailStat(
    releaseDate: String, rating: String, duration: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        FilmStatBox(
            statTitle = stringResource(id = R.string.release),
            statValue = releaseDate,
        )
        FilmStatBox(
            statTitle = stringResource(id = R.string.rating),
            statValue = rating,
        )
        FilmStatBox(
            statTitle = stringResource(id = R.string.runtime),
            statValue = duration,
        )
    }
}

@Composable
fun FilmStatBox(
    statTitle: String, statValue: String, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(all = 8.dp),
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = statTitle, style = MaterialTheme.typography.caption
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
    originalTitle: String,
    originalTitleRomanised: String,
    director: String,
    producer: String,
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
                text = " $originalTitle ($originalTitleRomanised)",
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
                text = " $director",
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
                text = " $producer",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
private fun FilmBannerImage(
    imageUrl: String, modifier: Modifier = Modifier
) {
    var isPainterLoading by remember {
        mutableStateOf(true)
    }

    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.film_banner),
        contentScale = ContentScale.FillBounds,
        onState = { state ->
            isPainterLoading = when (state) {
                is AsyncImagePainter.State.Loading -> true
                else -> false
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp)
            .alpha(.75f)
            .placeholder(
                visible = isPainterLoading,
                highlight = PlaceholderHighlight.fade(),
            )
    )
}

@Composable
private fun FilmPosterImage(
    imageUrl: String, modifier: Modifier = Modifier
) {
    var isPainterLoading by remember {
        mutableStateOf(true)
    }

    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(id = R.string.film_poster),
        onState = { state ->
            isPainterLoading = when (state) {
                is AsyncImagePainter.State.Loading -> true
                else -> false
            }
        },
        modifier = modifier
            .size(
                height = 150.dp,
                width = 100.dp,
            )
            .clip(MaterialTheme.shapes.small)
            .placeholder(
                visible = isPainterLoading,
                highlight = PlaceholderHighlight.fade(),
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
                film = dummyFilm,
                isLoading = false,
                onBackPressed = { },
                modifier = Modifier.padding(padding)
            )
        }
    }
}