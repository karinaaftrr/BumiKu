package com.example.bumiku.model
import com.example.bumiku.R

object NewsDetail {
    val dummyNews = listOf(
        News(
            id = 1,
            title = "Pentingnya Menanam Pohon",
            content = "Menanam pohon di lingkungan rumah dapat membantu menyerap karbon dioksida dan memberikan udara yang lebih segar.",
            date = "24 April 2026",
            gambar = R.drawable.hutan_mangrove
        ),
        News(
            id = 2,
            title = "Tips Mengurangi Sampah Plastik",
            content = "Mulai dengan membawa botol minum sendiri dan gunakan tas belanja kain untuk mengurangi polusi plastik di laut.",
            date = "23 April 2026",
            gambar = R.drawable.waste_plastik
        )
    )
}
