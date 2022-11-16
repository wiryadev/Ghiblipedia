package com.wiryadev.ghiblipedia.model

data class Film(
    val id: String,
    val title: String,
    val description: String,
    val posterUrl: String,
    val bannerUrl: String,
    val originalTitle: String,
    val originalTitleRomanised: String,
    val producer: String,
    val director: String,
    val releaseYear: String,
    val rating: String,
    val duration: String,
)