package com.example.bumiku.model

object NotifikasiSource {
    val dummyNotifications = listOf(
        Notifikasi(
            id = 1,
            title = "Aksi Bersih Pantai",
            message = "Jangan lupa besok ada aksi bersih pantai Kerang Mas pukul 08.00 WIB.",
            time = "2 jam yang lalu"
        ),
        Notifikasi(
            id = 2,
            title = "Eco Habit Selesai",
            message = "Selamat! Kamu telah menyelesaikan habit 'Pilah Sampah' hari ini.",
            time = "5 jam yang lalu"
        ),
        Notifikasi(
            id = 3,
            title = "Update Aplikasi",
            message = "Versi terbaru BumiKu sudah tersedia. Yuk update sekarang!",
            time = "1 hari yang lalu"
        ),
        Notifikasi(
            id = 4,
            title = "Tips Hari Ini",
            message = "Gunakan tas belanja kain untuk mengurangi penggunaan plastik sekali pakai.",
            time = "2 hari yang lalu"
        )
    )
}