package com.example.bumiku.model

object TaskSource {
    val dummyTasks = listOf(
        Task(
            id = 1,
            title = "Kurangi penggunaan plastik sekali pakai hari ini",
            description = "Hindari penggunaan plastik sekali pakai seperti sedotan, kantong plastik, dan botol plastik dalam aktivitas sehari-hari.",
            steps = listOf(
                "Siapkan tas belanja kain atau totebag sebagai pengganti kantong plastik.",
                "Gunakan tumbler atau botol minum reusable saat bepergian.",
                "Dokumentasikan aktivitas kamu dengan foto sebagai bukti."
            ),
            tip = "Tips: Simpan tas belanja kain di tas kamu agar selalu siap digunakan kapan saja.",
            estimasiWaktu = "60 menit",
            poin = "150 poin",
            isDone = true
        ),
        Task(
            id = 2,
            title = "Pisahkan sampah organik & anorganik",
            description = "Pisahkan sampah rumah tangga menjadi dua kategori: organik (sisa makanan, daun) dan anorganik (plastik, kertas, kaca).",
            steps = listOf(
                "Siapkan dua tempat sampah berbeda: satu untuk organik, satu untuk anorganik.",
                "Mulai pisahkan sampah dari dapur dan ruang makan.",
                "Dokumentasikan tempat sampah yang sudah dipisahkan dengan foto."
            ),
            tip = "Tips: Beri label warna pada tempat sampah agar mudah dibedakan oleh seluruh anggota keluarga.",
            estimasiWaktu = "30 menit",
            poin = "100 poin",
            isDone = true
        ),
        Task(
            id = 3,
            title = "Menanam 1 bibit pohon di lingkungan rumah",
            description = "Tanam bibit pohon di lingkungan rumah atau sekitarnya untuk mengurangi emisi karbon.",
            steps = listOf(
                "Siapkan bibit pohon (mangga, jambu, dll) dan pot atau lahan kecil.",
                "Gali lubang sedalam ± 15 cm, masukkan bibit, padatkan tanah di sekitarnya.",
                "Siram secukupnya dan beri label tanggal penanaman. Dokumentasikan dengan foto!"
            ),
            tip = "Tips: Pilih bibit pohon buah lokal agar lebih mudah tumbuh di iklim tropis.",
            estimasiWaktu = "±60 menit",
            poin = "150 poin",
            isDone = false
        ),
        Task(
            id = 4,
            title = "Matikan lampu saat tidak digunakan",
            description = "Biasakan mematikan lampu dan perangkat elektronik saat tidak digunakan untuk menghemat energi.",
            steps = listOf(
                "Periksa seluruh ruangan di rumah dan matikan lampu yang tidak digunakan.",
                "Cabut charger dan perangkat elektronik yang tidak sedang dipakai.",
                "Dokumentasikan aktivitas hemat energi kamu dengan foto."
            ),
            tip = "Tips: Pasang pengingat di saklar lampu untuk mengingatkan anggota keluarga mematikan lampu.",
            estimasiWaktu = "15 menit",
            poin = "75 poin",
            isDone = false
        ),
        Task(
            id = 5,
            title = "Gunakan sepeda atau transportasi umum",
            description = "Kurangi emisi karbon dengan menggunakan sepeda atau transportasi umum sebagai pengganti kendaraan pribadi.",
            steps = listOf(
                "Rencanakan perjalanan menggunakan transportasi umum atau sepeda.",
                "Lakukan perjalanan tanpa menggunakan kendaraan pribadi bermesin.",
                "Dokumentasikan perjalanan kamu dengan foto sebagai bukti."
            ),
            tip = "Tips: Gunakan aplikasi peta untuk menemukan rute transportasi umum yang paling efisien.",
            estimasiWaktu = "Menyesuaikan",
            poin = "200 poin",
            isDone = false
        )
    )
}