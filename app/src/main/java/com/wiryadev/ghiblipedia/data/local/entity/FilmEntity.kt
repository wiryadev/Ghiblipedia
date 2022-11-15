package com.wiryadev.ghiblipedia.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "running_time")
    val runningTime: String,
)