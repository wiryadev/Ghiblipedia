package com.wiryadev.ghiblipedia.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wiryadev.ghiblipedia.model.Film

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "movie_banner")
    val movieBanner: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "original_title_romanised")
    val originalTitleRomanised: String,
    @ColumnInfo(name = "producer")
    val producer: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "rt_score")
    val rtScore: String,
    @ColumnInfo(name = "running_time")
    val runningTime: String,
)

fun FilmEntity.asExternalModel() = Film(
    id = id,
    title = title,
    description = description,
    posterUrl = image,
    bannerUrl = movieBanner,
    originalTitle = originalTitle,
    originalTitleRomanised = originalTitleRomanised,
    producer = producer,
    director = director,
    releaseDate = releaseDate,
    rating = rtScore,
    duration = runningTime
)

fun Film.asEntity() = FilmEntity(
    id = id,
    title = title,
    description = description,
    director = director,
    image = posterUrl,
    movieBanner = bannerUrl,
    originalTitle = originalTitle,
    originalTitleRomanised = originalTitleRomanised,
    producer = producer,
    releaseDate = releaseDate,
    runningTime = duration,
    rtScore = rating
)