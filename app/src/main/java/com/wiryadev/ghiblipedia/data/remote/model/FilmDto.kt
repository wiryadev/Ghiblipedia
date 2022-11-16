package com.wiryadev.ghiblipedia.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.wiryadev.ghiblipedia.model.Film

@JsonClass(generateAdapter = true)
data class FilmDto(
    @Json(name = "description")
    val description: String,
    @Json(name = "director")
    val director: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "locations")
    val locations: List<String>,
    @Json(name = "movie_banner")
    val movieBanner: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_title_romanised")
    val originalTitleRomanised: String,
    @Json(name = "people")
    val people: List<String>,
    @Json(name = "producer")
    val producer: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "rt_score")
    val rtScore: String,
    @Json(name = "running_time")
    val runningTime: String,
    @Json(name = "species")
    val species: List<String>,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "vehicles")
    val vehicles: List<String>
)

fun FilmDto.asExternalModel() = Film(
    id = id,
    title = title,
    description = description,
    posterUrl = image,
    bannerUrl = movieBanner,
    originalTitle = originalTitle,
    originalTitleRomanised = originalTitleRomanised,
    producer = producer,
    releaseDate = releaseDate,
    rating = rtScore,
    duration = runningTime,
    director = director,
)
