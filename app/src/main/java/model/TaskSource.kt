package com.example.bumiku.model

object TaskSource {
    val dummyTasks = listOf(
        Task(
            id = 1, day = 1,
            title = "Pisahkan Sampah Organik & Anorganik",
            description = "Pisahkan sisa makanan dengan sampah plastik atau kertas di tempat sampah berbeda.",
            steps = listOf("Siapkan 2 wadah.", "Wadah A untuk sisa makanan (organik).", "Wadah B untuk plastik/kertas (anorganik).", "Lakukan seharian penuh."),
            tip = "Gunakan label warna hijau untuk organik dan kuning untuk anorganik.",
            estimasiWaktu = "Full Day", poin = "50 poin"
        ),
        Task(
            id = 2, day = 1,
            title = "Gunakan Tas Belanja Kain (Totebag)",
            description = "Bawa tas sendiri saat belanja untuk menghindari penggunaan kantong plastik sekali pakai.",
            steps = listOf("Siapkan totebag di motor/tas.", "Gunakan saat membeli barang.", "Tolak kantong plastik dari kasir."),
            tip = "Satu totebag bisa menggantikan ratusan kantong plastik dalam setahun.",
            estimasiWaktu = "30 menit", poin = "30 poin"
        ),
        Task(
            id = 3, day = 1,
            title = "Hindari Sedotan Plastik",
            description = "Minum langsung dari gelas atau gunakan sedotan reusable (stainless/bambu).",
            steps = listOf("Pesan minuman tanpa sedotan.", "Bawa sedotan sendiri jika perlu.", "Edukasi teman di sekitarmu."),
            tip = "Sedotan plastik adalah salah satu pencemar laut terbesar.",
            estimasiWaktu = "10 menit", poin = "20 poin"
        ),
        Task(
            id = 4, day = 1,
            title = "Bersihkan Wadah Plastik Bekas",
            description = "Cuci bersih kemasan plastik bekas makanan/minuman agar bisa didaur ulang dengan baik.",
            steps = listOf("Kumpulkan botol/cup plastik.", "Bilas dengan air mengalir.", "Keringkan sebelum dibuang ke tong anorganik."),
            tip = "Sampah yang kotor sulit untuk diproses oleh mesin daur ulang.",
            estimasiWaktu = "15 menit", poin = "25 poin"
        ),

        Task(
            id = 5, day = 2,
            title = "Siapkan Wadah Kompos Sederhana",
            description = "Membuat tempat pengolahan sisa sayur menjadi pupuk organik.",
            steps = listOf("Cari ember bekas.", "Lubangi bagian bawahnya.", "Masukkan tanah sebagai lapisan dasar."),
            tip = "Kompos mengurangi beban TPA secara signifikan.",
            estimasiWaktu = "45 menit", poin = "60 poin"
        ),
        Task(
            id = 6, day = 2,
            title = "Cacah Sisa Sayuran Dapur",
            description = "Mempercepat proses pengomposan dengan memperkecil ukuran bahan organik.",
            steps = listOf("Kumpulkan kulit buah/sisa sayur.", "Potong kecil-kecil menggunakan pisau.", "Masukkan ke wadah kompos."),
            tip = "Jangan masukkan daging atau minyak ke dalam komposter.",
            estimasiWaktu = "20 menit", poin = "40 poin"
        ),
        Task(
            id = 7, day = 2,
            title = "Gunakan Air Cucian Beras untuk Tanaman",
            description = "Memanfaatkan limbah cair dapur sebagai nutrisi alami tanaman.",
            steps = listOf("Tampung air cucian beras.", "Diamkan sejenak.", "Siramkan ke tanaman di halaman."),
            tip = "Air beras mengandung vitamin B1 yang baik untuk akar tanaman.",
            estimasiWaktu = "5 menit", poin = "15 poin"
        ),
        Task(
            id = 8, day = 2,
            title = "Kumpulkan Kulit Telur",
            description = "Kulit telur kaya akan kalsium untuk menyuburkan tanah.",
            steps = listOf("Cuci kulit telur bekas masak.", "Keringkan di bawah sinar matahari.", "Hancurkan hingga halus dan tabur ke tanah."),
            tip = "Kulit telur yang hancur juga mencegah hama siput.",
            estimasiWaktu = "10 menit", poin = "20 poin"
        ),

        Task(
            id = 9, day = 3,
            title = "Bawa Tumbler Sendiri",
            description = "Mengurangi sampah botol plastik kemasan dengan membawa botol minum pribadi.",
            steps = listOf("Isi air minum dari rumah.", "Bawa saat bekerja atau sekolah.", "Isi ulang di water station jika tersedia."),
            tip = "Membawa tumbler juga menghemat pengeluaran uangmu.",
            estimasiWaktu = "Full Day", poin = "35 poin"
        ),
        Task(
            id = 10, day = 3,
            title = "Gunakan Kotak Makan Reusable",
            description = "Membeli makanan tanpa menggunakan bungkus kertas atau styrofoam.",
            steps = listOf("Bawa kotak makan saat beli sarapan.", "Minta penjual menaruh makanan di wadahmu.", "Cuci bersih setelah digunakan."),
            tip = "Styrofoam membutuhkan waktu 500 tahun untuk hancur.",
            estimasiWaktu = "20 menit", poin = "45 poin"
        ),
        Task(
            id = 11, day = 3,
            title = "Menanam 1 Bibit Pohon",
            description = "Menambah area hijau di lingkungan rumah.",
            steps = listOf("Siapkan bibit tanaman.", "Gali lubang 15cm.", "Tanam dan padatkan tanah.", "Siram secara rutin."),
            tip = "Pohon menghasilkan oksigen dan mendinginkan suhu rumah.",
            estimasiWaktu = "60 menit", poin = "100 poin"
        ),
        Task(
            id = 12, day = 3,
            title = "Gunakan Sapu Tangan",
            description = "Mengurangi penggunaan tisu sekali pakai.",
            steps = listOf("Siapkan sapu tangan kain.", "Gunakan untuk mengelap keringat/tangan.", "Cuci secara berkala."),
            tip = "Produksi tisu memakan jutaan pohon setiap tahunnya.",
            estimasiWaktu = "Full Day", poin = "25 poin"
        ),

        Task(
            id = 13, day = 4,
            title = "Gunakan Kertas Dua Sisi",
            description = "Memaksimalkan penggunaan kertas untuk mencatat atau print.",
            steps = listOf("Cari kertas yang satu sisinya masih kosong.", "Gunakan untuk coretan/catatan.", "Simpan kertas bekas untuk keperluan darurat."),
            tip = "Gunakan teknologi digital jika memungkinkan untuk paperless.",
            estimasiWaktu = "15 menit", poin = "20 poin"
        ),
        Task(
            id = 14, day = 4,
            title = "Kumpulkan Koran/Majalah Bekas",
            description = "Menyiapkan sampah kertas untuk disalurkan ke bank sampah.",
            steps = listOf("Cari koran/majalah lama.", "Ikat dengan rapi.", "Simpan di tempat kering."),
            tip = "Kertas yang basah nilainya turun saat dijual ke pengepul.",
            estimasiWaktu = "30 menit", poin = "40 poin"
        ),
        Task(
            id = 15, day = 4,
            title = "Buat Amplop dari Kertas Bekas",
            description = "Kreativitas daur ulang kertas menjadi barang fungsional.",
            steps = listOf("Siapkan kertas kado atau kalender bekas.", "Lipat sesuai pola amplop.", "Lem bagian pinggirnya."),
            tip = "Daur ulang kreatif memperpanjang usia pakai sebuah benda.",
            estimasiWaktu = "20 menit", poin = "35 poin"
        ),
        Task(
            id = 16, day = 4,
            title = "Hentikan Langganan Brosur Fisik",
            description = "Beralih ke katalog digital untuk mengurangi sampah kertas.",
            steps = listOf("Cek brosur yang sering datang ke rumah.", "Minta pihak terkait mengirim versi PDF.", "Berhenti menumpuk brosur di meja."),
            tip = "Katalog digital lebih mudah dicari dan tidak mengotori rumah.",
            estimasiWaktu = "10 menit", poin = "25 poin"
        ),

        Task(
            id = 17, day = 5,
            title = "Pisahkan Baterai Bekas",
            description = "Mencegah kebocoran zat kimia baterai ke tanah.",
            steps = listOf("Cari baterai remot/jam yang habis.", "Masukkan ke botol plastik khusus.", "Jangan campur dengan sampah biasa."),
            tip = "Baterai mengandung merkuri yang sangat beracun bagi tanah.",
            estimasiWaktu = "10 menit", poin = "50 poin"
        ),
        Task(
            id = 18, day = 5,
            title = "Kumpulkan Kabel Rusak",
            description = "Manajemen sampah elektronik (E-Waste) di rumah.",
            steps = listOf("Cari kabel charger/headset rusak.", "Gulung dengan rapi.", "Satukan dalam wadah e-waste."),
            tip = "Sampah elektronik mengandung logam berharga yang bisa diekstrak kembali.",
            estimasiWaktu = "15 menit", poin = "30 poin"
        ),
        Task(
            id = 19, day = 5,
            title = "Donasikan Pakaian Layak Pakai",
            description = "Mengurangi limbah tekstil dengan memperpanjang masa pakai baju.",
            steps = listOf("Sortir lemari pakaian.", "Pilih baju yang jarang dipakai tapi bagus.", "Cuci bersih dan bungkus rapi."),
            tip = "Industri fashion adalah polutan air terbesar kedua di dunia.",
            estimasiWaktu = "60 menit", poin = "80 poin"
        ),
        Task(
            id = 20, day = 5,
            title = "Matikan Lampu Saat Tidur",
            description = "Menghemat energi listrik dan mengurangi emisi karbon.",
            steps = listOf("Pastikan semua lampu ruangan mati.", "Gunakan lampu tidur watt kecil jika perlu.", "Cabut charger HP yang tidak dipakai."),
            tip = "Hemat energi sama dengan menyelamatkan bumi dari pemanasan global.",
            estimasiWaktu = "5 menit", poin = "40 poin"
        ),

        Task(
            id = 21, day = 6,
            title = "Pungut Sampah di Sekitar Rumah",
            description = "Aksi nyata membersihkan lingkungan dari sampah yang tercecer.",
            steps = listOf("Gunakan sarung tangan.", "Bawa kantong sampah.", "Punguti plastik/puntung rokok di jalan depan rumah."),
            tip = "Lingkungan bersih mencerminkan masyarakat yang beradab.",
            estimasiWaktu = "30 menit", poin = "70 poin"
        ),
        Task(
            id = 22, day = 6,
            title = "Edukasi Keluarga Tentang Sampah",
            description = "Berbagi ilmu cara memilah sampah kepada orang rumah.",
            steps = listOf("Ajak diskusi saat makan.", "Jelaskan pentingnya memilah sampah.", "Tunjukkan lokasi tempat sampah baru."),
            tip = "Perubahan besar dimulai dari unit terkecil yaitu keluarga.",
            estimasiWaktu = "20 menit", poin = "50 poin"
        ),
        Task(
            id = 23, day = 6,
            title = "Share Tips Lingkungan di Medsos",
            description = "Menggunakan platform digital untuk menyebarkan kesadaran lingkungan.",
            steps = listOf("Foto aktivitas pemilahan sampahmu.", "Tulis caption inspiratif.", "Gunakan hashtag #BumiKu #ZeroWaste."),
            tip = "Satu postinganmu bisa menginspirasi banyak orang untuk berubah.",
            estimasiWaktu = "15 menit", poin = "60 poin"
        ),
        Task(
            id = 24, day = 6,
            title = "Cari Lokasi Bank Sampah Terdekat",
            description = "Mengetahui kemana sampah anorganik harus disetorkan.",
            steps = listOf("Buka Google Maps.", "Cari kata kunci 'Bank Sampah'.", "Catat alamat dan jam operasionalnya."),
            tip = "Sampah yang disetor ke Bank Sampah bisa menghasilkan uang tabungan.",
            estimasiWaktu = "10 menit", poin = "30 poin"
        ),

        Task(
            id = 25, day = 7,
            title = "Hari Tanpa Plastik Sekali Pakai",
            description = "Komitmen penuh tidak menghasilkan sampah plastik dalam 24 jam.",
            steps = listOf("Bawa semua alat reusable.", "Jangan beli makanan kemasan plastik.", "Gunakan botol minum isi ulang."),
            tip = "Jika kamu bisa melakukannya sehari, kamu bisa melakukannya selamanya.",
            estimasiWaktu = "Full Day", poin = "150 poin"
        ),
        Task(
            id = 26, day = 7,
            title = "Belanja di Toko Curah (Bulk Store)",
            description = "Membeli kebutuhan tanpa kemasan plastik dari pabrik.",
            steps = listOf("Bawa toples/wadah dari rumah.", "Isi deterjen/beras sesuai kebutuhan.", "Timbang dan bayar."),
            tip = "Bulk store adalah cara terbaik belanja tanpa sampah.",
            estimasiWaktu = "45 menit", poin = "90 poin"
        ),
        Task(
            id = 27, day = 7,
            title = "Bersihkan Area Komposter",
            description = "Merawat fasilitas pengolahan sampah organik agar tetap produktif.",
            steps = listOf("Aduk tumpukan kompos.", "Tambahkan air jika terlalu kering.", "Pastikan tidak ada bau menyengat."),
            tip = "Oksigen sangat diperlukan mikroba untuk mengurai sampah.",
            estimasiWaktu = "20 menit", poin = "50 poin"
        ),
        Task(
            id = 28, day = 7,
            title = "Evaluasi Sampah Mingguan",
            description = "Melihat perkembangan pengurangan sampahmu selama seminggu.",
            steps = listOf("Cek berat sampah yang dihasilkan.", "Catat apa yang paling banyak dibuang.", "Buat target untuk minggu depan."),
            tip = "Evaluasi membantumu melihat dampak nyata dari setiap aksi kecilmu.",
            estimasiWaktu = "30 menit", poin = "100 poin"
        )
    )
}
