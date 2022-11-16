package com.wiryadev.ghiblipedia.data

import com.wiryadev.ghiblipedia.data.remote.model.FilmDto
import com.wiryadev.ghiblipedia.data.remote.retrofit.GhibliService

class FakeService : GhibliService {

    override suspend fun getFilms(): List<FilmDto> {
        return FakeServiceDataSource.films
    }

    override suspend fun getFilmDetail(filmId: String): FilmDto {
        return FakeServiceDataSource.films.first {
            it.id == filmId
        }
    }
}

object FakeServiceDataSource {
    val films = listOf(
        FilmDto(
            description = "The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa. With the help of resourceful Pazu and a rollicking band of sky pirates, she makes her way to the ruins of the once-great civilization. Sheeta and Pazu must outwit the evil Muska, who plans to use Laputa's science to make himself ruler of the world.",
            director = "Hayao Miyazaki",
            id = "2baf70d1-42bb-4437-b551-e5fed5a87abe",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg",
            locations = listOf(),
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/3cyjYtLWCBE1uvWINHFsFnE8LUK.jpg",
            originalTitle = "天空の城ラピュタ",
            originalTitleRomanised = "Tenkū no shiro Rapyuta",
            people = listOf(),
            producer = "Isao Takahata",
            releaseDate = "1986",
            rtScore = "95",
            runningTime = "124",
            species = listOf(),
            title = "Castle in the Sky",
            url = "https://ghibliapi.herokuapp.com/films/2baf70d1-42bb-4437-b551-e5fed5a87abe",
            vehicles = listOf()
        ),
        FilmDto(
            description = "In the latter part of World War II, a boy and his sister, orphaned when their mother is killed in the firebombing of Tokyo, are left to survive on their own in what remains of civilian life in Japan. The plot follows this boy and his sister as they do their best to survive in the Japanese countryside, battling hunger, prejudice, and pride in their own quiet, personal battle.",
            director = "Isao Takahata",
            id = "12cfb892-aac0-4c5b-94af-521852e46d6a",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg",
            locations = listOf(),
            movieBanner = "https://image.tmdb.org/t/p/original/vkZSd0Lp8iCVBGpFH9L7LzLusjS.jpg",
            originalTitle = "火垂るの墓",
            originalTitleRomanised = "",
            people = listOf(),
            producer = "Toru Hara",
            releaseDate = "1988",
            rtScore = "97",
            runningTime = "89",
            species = listOf(),
            title = "Grave of the Fireflies",
            url = "https://ghibliapi.herokuapp.com/films/12cfb892-aac0-4c5b-94af-521852e46d6a",
            vehicles = listOf()
        ),
        FilmDto(
            description = "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home, the older sister seeks help from the spirits to find her.",
            director = "Hayao Miyazaki",
            id = "58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rtGDOeG9LzoerkDGZF9dnVeLppL.jpg",
            locations = listOf(),
            movieBanner = "https://image.tmdb.org/t/p/original/etqr6fOOCXQOgwrQXaKwenTSuzx.jpg",
            originalTitle = "となりのトトロ",
            originalTitleRomanised = "Tonari no Totoro",
            people = listOf(),
            producer = "Hayao Miyazaki",
            releaseDate = "1988",
            rtScore = "93",
            runningTime = "86",
            species = listOf(),
            title = "My Neighbor Totoro",
            url = "https://ghibliapi.herokuapp.com/films/58611129-2dbc-4a81-a72f-77ddfc1b1b49",
            vehicles = listOf()
        ),
        FilmDto(
            description = "A young witch, on her mandatory year of independent life, finds fitting into a new community difficult while she supports herself by running an air courier service.",
            director = "Hayao Miyazaki",
            id = "ea660b10-85c4-4ae3-8a5f-41cea3648e3e",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/7nO5DUMnGUuXrA4r2h6ESOKQRrx.jpg",
            locations = listOf(),
            movieBanner = "https://image.tmdb.org/t/p/original/h5pAEVma835u8xoE60kmLVopLct.jpg",
            originalTitle = "魔女の宅急便",
            originalTitleRomanised = "Majo no takkyūbin",
            people = listOf(),
            producer = "Hayao Miyazaki",
            releaseDate = "1989",
            rtScore = "96",
            runningTime = "102",
            species = listOf(),
            title = "Kiki's Delivery Service",
            url = "https://ghibliapi.herokuapp.com/films/ea660b10-85c4-4ae3-8a5f-41cea3648e3e",
            vehicles = listOf()
        ),
        FilmDto(
            description = "It’s 1982, and Taeko is 27 years old, unmarried, and has lived her whole life in Tokyo. She decides to visit her family in the countryside, and as the train travels through the night, memories flood back of her younger years: the first immature stirrings of romance, the onset of puberty, and the frustrations of math and boys. At the station she is met by young farmer Toshio, and the encounters with him begin to reconnect her to forgotten longings. In lyrical switches between the present and the past, Taeko contemplates the arc of her life, and wonders if she has been true to the dreams of her childhood self.",
            director = "Isao Takahata",
            id = "4e236f34-b981-41c3-8c65-f8c9000b94e7",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/xjJU6rwzLX7Jk8HFQfVW6H5guMC.jpg",
            locations = listOf(),
            movieBanner = "https://image.tmdb.org/t/p/w533_and_h300_bestv2/isCrlWWI4JrdLKAUAwFb5cjAsH4.jpg",
            originalTitle = "おもひでぽろぽろ",
            originalTitleRomanised = "Omoide poro poro",
            people = listOf(),
            producer = "Toshio Suzuki",
            releaseDate = "1991",
            rtScore = "100",
            runningTime = "118",
            species = listOf(),
            title = "Only Yesterday",
            url = "https://ghibliapi.herokuapp.com/films/4e236f34-b981-41c3-8c65-f8c9000b94e7",
            vehicles = listOf()
        ),

    )
}