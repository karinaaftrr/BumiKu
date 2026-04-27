package com.example.bumiku.model

import com.example.bumiku.R

object WasteSource {
    val categories = listOf(
        WasteCategory(
            "organik", "Sampah Organik", "🌿", R.drawable.waste_organik,
            "Sampah yang berasal dari makhluk hidup dan dapat terurai secara alami.",
            listOf("Sisa Makanan", "Daun Kering", "Kulit Buah"),
            listOf("Cara Membuat Kompos dari Sisa Makanan", "Cara Membuat Pupuk Cair Organik")
        ),
        WasteCategory(
            "plastik", "Plastik", "♻️", R.drawable.waste_plastik,
            "Limbah polimer sintetis yang sulit terurai dan memerlukan penanganan khusus.",
            listOf("Kantong Plastik", "Bungkus Makanan", "Botol Plastik"),
            listOf("Cara Mendaur Ulang Kantong Plastik dengan Kerajinan", "Cara Membuat Pot dari Bungkus Makanan")
        ),
        WasteCategory(
            "kertas", "Kertas", "📄", R.drawable.waste_kertas,
            "Limbah berbahan serat selulosa yang bisa didaur ulang menjadi kertas baru.",
            listOf("Koran Bekas", "Kardus", "Kertas HVS"),
            listOf("Cara Membuat Kertas Daur Ulang di Rumah", "Cara Membuat Kerajinan dari Kardus")
        ),
        WasteCategory(
            "logam", "Logam", "⚙️", R.drawable.waste_logam,
            "Limbah metal yang dapat dilelehkan dan dibentuk ulang menjadi produk baru.",
            listOf("Kaleng Minuman", "Besi Tua", "Aluminium Foil"),
            listOf("Cara Membuat Kerajinan dari Kaleng Bekas", "Cara Menjual Logam ke Pengepul")
        ),
        WasteCategory(
            "berbahaya", "Limbah Berbahaya", "⚠️", R.drawable.waste_berbahaya,
            "Sampah mengandung zat beracun yang berbahaya bagi kesehatan dan lingkungan.",
            listOf("Baterai Bekas", "Lampu Neon", "Kemasan Pestisida"),
            listOf("Cara Membuang Baterai Bekas dengan Aman", "Cara Menangani Limbah Elektronik")
        )
    )
}