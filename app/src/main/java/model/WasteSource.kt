package com.example.bumiku.model

import com.example.bumiku.R

object WasteSource {
    val categories = listOf(
        WasteCategory(
            "organik", "Sampah Organik", "🌿", R.drawable.waste_organik,
            "Sampah yang berasal dari makhluk hidup dan dapat terurai secara alami.",
            listOf("Sisa Makanan", "Daun Kering", "Kulit Buah"),
            listOf("Tutorial Membuat Kompos", "Tutorial Pupuk Organik")
        ),
        WasteCategory(
            "plastik", "Plastik", "♻️", R.drawable.waste_plastik,
            "Limbah polimer sintetis yang sulit terurai dan memerlukan penanganan khusus.",
            listOf("Kantong Plastik", "Bungkus Makanan", "Botol Plastik"),
            listOf(
                "Mendaur Ulang Plastik",
                "Pot dari Bungkus Makanan"
            )
        ),
        WasteCategory(
            "kertas", "Kertas", "📄", R.drawable.waste_kertas,
            "Limbah berbahan serat selulosa yang bisa didaur ulang menjadi kertas baru.",
            listOf("Koran Bekas", "Kardus", "Kertas HVS"),
            listOf("Tutorial Kertas Daur Ulang", "Tutorial Kerajinan Kardus")
        ),
        WasteCategory(
            "logam", "Logam", "⚙️", R.drawable.waste_logam,
            "Limbah metal yang dapat dilelehkan dan dibentuk ulang menjadi produk baru.",
            listOf("Kaleng Minuman", "Besi Tua", "Aluminium Foil"),
            listOf("Kerajinan Kaleng Bekas", "Menjual Logam")
        ),
        WasteCategory(
            "berbahaya", "Limbah Berbahaya", "⚠️", R.drawable.waste_berbahaya,
            "Sampah mengandung zat beracun yang berbahaya bagi kesehatan dan lingkungan.",
            listOf("Baterai Bekas", "Lampu Neon", "Kemasan Pestisida"),
            listOf("Tutorial Baterai Bekas", "Limbah Elektronik")
        )
    )
}