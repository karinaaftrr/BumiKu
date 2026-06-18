package com.example.bumiku.ui.wguide

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bumiku.data.model.WasteCategory
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

private sealed class GuideDetailState {
    object Main : GuideDetailState()
    object AllManagement : GuideDetailState()
    object AllRecycling : GuideDetailState()
    data class Material(val name: String, val label: String) : GuideDetailState()
}

@Composable
fun DetailWasteGuideScreen(
    category: WasteCategory,
    navController: NavController
) {
    val screenState = remember {
        mutableStateOf<GuideDetailState>(GuideDetailState.Main)
    }

    when (val state = screenState.value) {
        is GuideDetailState.Main -> {
            MainDetailContent(
                category = category,
                navController = navController,
                onShowManagement = {
                    screenState.value = GuideDetailState.AllManagement
                },
                onShowRecycling = {
                    screenState.value = GuideDetailState.AllRecycling
                },
                onSelectItem = { name, label ->
                    screenState.value = GuideDetailState.Material(name, label)
                }
            )
        }

        is GuideDetailState.AllManagement -> {
            AllItemsScreen(
                category = category,
                items = category.managementItems,
                sectionTitle = "Semua Cara Pengelolaan",
                onBack = {
                    screenState.value = GuideDetailState.Main
                },
                onItemClick = { name ->
                    screenState.value =
                        GuideDetailState.Material(name, "Cara Pengelolaan")
                }
            )
        }

        is GuideDetailState.AllRecycling -> {
            AllItemsScreen(
                category = category,
                items = category.recyclingTips,
                sectionTitle = "Semua Tips Daur Ulang",
                onBack = {
                    screenState.value = GuideDetailState.Main
                },
                onItemClick = { name ->
                    screenState.value =
                        GuideDetailState.Material(name, "Tips Daur Ulang")
                }
            )
        }

        is GuideDetailState.Material -> {
            MaterialScreen(
                category = category,
                title = state.name,
                sectionLabel = state.label,
                onBack = {
                    screenState.value = GuideDetailState.Main
                }
            )
        }
    }
}

@Composable
private fun MainDetailContent(
    category: WasteCategory,
    navController: NavController,
    onShowManagement: () -> Unit,
    onShowRecycling: () -> Unit,
    onSelectItem: (String, String) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                color = GreenDeep,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Text(
                        text = category.name,
                        color = GoldYellow,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 48.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(
                        1.dp,
                        BlackSolid.copy(alpha = 0.05f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Tentang ${category.name}",
                            style = MaterialTheme.typography.titleSmall,
                            color = GreenDeep,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = category.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = BlackSolid.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cara Pengelolaan",
                        style = MaterialTheme.typography.titleMedium,
                        color = BlackSolid,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Lihat semua",
                        style = MaterialTheme.typography.labelSmall,
                        color = GreenDeep,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            onShowManagement()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(category.managementItems) { _, itemName ->
                        ManagementCard(
                            name = itemName,
                            imageName = category.imageName
                        ) {
                            onSelectItem(itemName, "Cara Pengelolaan")
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tips Daur Ulang",
                        style = MaterialTheme.typography.titleMedium,
                        color = BlackSolid,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Lihat semua",
                        style = MaterialTheme.typography.labelSmall,
                        color = GreenDeep,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            onShowRecycling()
                        }
                    )
                }
            }

            itemsIndexed(category.recyclingTips) { _, tip ->
                RecyclingTipCard(
                    title = tip,
                    imageName = category.imageName
                ) {
                    onSelectItem(tip, "Tips Daur Ulang")
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun MaterialScreen(
    category: WasteCategory,
    title: String,
    sectionLabel: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                color = GreenDeep,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                onBack()
                            }
                    )

                    Text(
                        text = title,
                        color = GoldYellow,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 48.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(
                    id = getGuideImage(category.imageName)
                ),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Surface(
                    color = GreenDeep,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "${category.icon} ${category.name} · $sectionLabel",
                        color = GoldYellow,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp
                    ),
                    color = BlackSolid,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(
                        1.dp,
                        BlackSolid.copy(alpha = 0.05f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Panduan Lengkap",
                            style = MaterialTheme.typography.titleSmall,
                            color = GreenDeep,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Pelajari cara mengelola $title sebagai bagian dari ${category.name}. Pengelolaan yang tepat membantu mengurangi dampak buruk terhadap lingkungan dan membuat sampah lebih mudah dipilah.",
                            color = BlackSolid.copy(alpha = 0.7f),
                            lineHeight = 21.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Langkah sederhana yang bisa dilakukan:",
                            color = BlackSolid,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 21.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "1. Pisahkan sampah sesuai jenisnya.\n2. Bersihkan sampah jika masih bisa didaur ulang.\n3. Simpan pada tempat yang aman dan kering.\n4. Gunakan kembali jika masih layak.\n5. Serahkan ke bank sampah atau tempat pengelolaan yang sesuai.",
                            color = BlackSolid.copy(alpha = 0.7f),
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AllItemsScreen(
    category: WasteCategory,
    items: List<String>,
    sectionTitle: String,
    onBack: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                color = GreenDeep,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                onBack()
                            }
                    )

                    Text(
                        text = sectionTitle,
                        color = GoldYellow,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 48.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(items) { _, item ->
                RecyclingTipCard(
                    title = item,
                    imageName = category.imageName
                ) {
                    onItemClick(item)
                }
            }
        }
    }
}

@Composable
private fun ManagementCard(
    name: String,
    imageName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(
                    id = getGuideImage(imageName)
                ),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GoldYellow)
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "$name >",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun RecyclingTipCard(
    title: String,
    imageName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = getGuideImage(imageName)
                ),
                contentDescription = title,
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = BlackSolid,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = GoldYellow
                ) {
                    Text(
                        text = "Lihat materi",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
    }
}