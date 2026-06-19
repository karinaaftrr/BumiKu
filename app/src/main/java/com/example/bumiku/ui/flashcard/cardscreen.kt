package com.example.bumiku.ui.flashcard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.data.model.FlashCard
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(onBackClick: () -> Unit) {
    // 30 Soal Flashcards
    val allFlashCards = remember {
        listOf(
            FlashCard("1", "Apa dampak utama penggunaan plastik sekali pakai?", "Pencemaran laut dan mikroplastik yang masuk ke rantai makanan."),
            FlashCard("2", "Apa itu Greenwashing?", "Strategi pemasaran perusahaan agar terlihat ramah lingkungan padahal aslinya tidak."),
            FlashCard("3", "Berapa lama waktu yang dibutuhkan botol plastik untuk terurai?", "Sekitar 450 tahun."),
            FlashCard("4", "Apa itu Zero Waste?", "Gaya hidup yang meminimalkan produksi sampah agar tidak berakhir di TPA."),
            FlashCard("5", "Apa kepanjangan dari 3R?", "Reduce, Reuse, Recycle."),
            FlashCard("6", "Apa manfaat menanam pohon bagi lingkungan?", "Menyerap CO2, menghasilkan oksigen, dan mencegah erosi."),
            FlashCard("7", "Apa itu pemanasan global?", "Peningkatan suhu rata-rata atmosfer, laut, dan daratan Bumi."),
            FlashCard("8", "Apa yang dimaksud dengan sampah organik?", "Sampah yang berasal dari sisa makhluk hidup dan mudah terurai."),
            FlashCard("9", "Apa contoh sampah anorganik?", "Plastik, logam, kaca, dan kaleng."),
            FlashCard("10", "Apa itu kompos?", "Hasil penguraian bahan organik yang dapat digunakan sebagai pupuk alami."),
            FlashCard("11", "Mengapa kita harus menghemat listrik?", "Sebagian besar listrik dihasilkan dari bahan bakar fosil yang merusak lingkungan."),
            FlashCard("12", "Apa dampak penebangan hutan secara liar?", "Hilangnya habitat hewan, banjir, dan peningkatan gas rumah kaca."),
            FlashCard("13", "Apa itu emisi karbon?", "Gas yang dilepaskan dari pembakaran bahan bakar fosil yang mengandung karbon."),
            FlashCard("14", "Apa fungsi lapisan ozon?", "Melindungi Bumi dari radiasi ultraviolet (UV) yang berbahaya dari matahari."),
            FlashCard("15", "Apa itu energi terbarukan?", "Energi dari proses alam yang berkelanjutan, seperti matahari dan angin."),
            FlashCard("16", "Apa cara mudah mengurangi sampah?", "Membawa tas belanja sendiri dan menghindari kemasan plastik."),
            FlashCard("17", "Apa itu ekosistem?", "Hubungan timbal balik antara makhluk hidup dengan lingkungannya."),
            FlashCard("18", "Mengapa sampah elektronik berbahaya?", "Mengandung logam berat seperti merkuri yang mencemari tanah."),
            FlashCard("19", "Apa itu jejak karbon?", "Total emisi gas rumah kaca yang dihasilkan oleh individu atau produk."),
            FlashCard("20", "Apa manfaat bersepeda bagi lingkungan?", "Mengurangi polusi udara dan emisi gas buang kendaraan."),
            FlashCard("21", "Apa itu perubahan iklim?", "Perubahan pola cuaca jangka panjang yang signifikan secara global."),
            FlashCard("22", "Apa itu upcycling?", "Mengolah barang bekas menjadi barang baru dengan nilai lebih tinggi."),
            FlashCard("23", "Mengapa harus membawa botol minum sendiri?", "Untuk mengurangi penggunaan botol plastik sekali pakai."),
            FlashCard("24", "Apa itu sampah B3?", "Bahan Berbahaya dan Beracun yang merusak lingkungan dan kesehatan."),
            FlashCard("25", "Apa peran terumbu karang?", "Menjadi habitat spesies laut dan pelindung pantai dari ombak."),
            FlashCard("26", "Bagaimana membuang minyak jelantah?", "Dikumpulkan untuk diolah menjadi biodiesel, jangan dibuang ke selokan."),
            FlashCard("27", "Apa itu fast fashion?", "Industri pakaian murah yang diproduksi cepat dan merusak lingkungan."),
            FlashCard("28", "Apa tujuan Hari Bumi?", "Meningkatkan kesadaran masyarakat untuk menjaga planet Bumi."),
            FlashCard("29", "Apa itu mikroplastik?", "Potongan plastik sangat kecil (<5mm) yang berbahaya bagi biota laut."),
            FlashCard("30", "Apa manfaat sedotan stainless?", "Mengurangi sampah sedotan plastik yang mencemari lautan.")
        )
    }

    // Mengambil 6 soal secara acak
    var shuffleKey by remember { mutableIntStateOf(0) }
    val displayedCards = remember(shuffleKey) {
        allFlashCards.shuffled().take(6)
    }

    val pagerState = rememberPagerState(shuffleKey, pageCount = { displayedCards.size })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Wcard - Eco Flashcards", color = GoldYellow, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = GoldYellow)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = GreenDeep)
            )
        },
        containerColor = GreenDeep
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 48.dp),
                modifier = Modifier.fillMaxHeight(0.7f)
            ) { page ->
                FlipCard(displayedCards[page])
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Klik kartu untuk melihat jawaban\nGeser ke samping untuk pertanyaan lain",
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { shuffleKey++ },
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                shape = RoundedCornerShape(50)
            ) {
                Text("🔀 Acak Ulang", color = GreenDeep, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun FlipCard(card: FlashCard) {
    var rotated by remember { mutableStateOf(false) }
    
    // Reset status kartu saat kartu di dalam pager berubah (opsional, tapi bagus agar user tidak bingung)
    LaunchedEffect(card) {
        rotated = false
    }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "flip"
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { rotated = !rotated },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (rotation <= 90f) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("PERTANYAAN", color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = card.question,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = GreenDeep,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .graphicsLayer { rotationY = 180f },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("JAWABAN", color = GoldYellow, style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = card.answer,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
