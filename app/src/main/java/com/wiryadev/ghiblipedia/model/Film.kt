package com.wiryadev.ghiblipedia.model

data class Film(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val movieBannerUrl: String,
    val originalTitle: String,
    val originalTitleRomanised: String,
    val producer: String,
    val director: String,
    val releaseDate: String,
    val rtScore: String,
    val duration: String,
)