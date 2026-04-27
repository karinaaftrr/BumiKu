package com.example.bumiku.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person

object FeatureSource {
    val features = listOf(
        Feature("WGuide (Panduan Sampah)", "waste_guide", Icons.Default.Info),
        Feature("WTrack (Tracker Aktivitas)", "tracker", Icons.Default.BarChart),
        Feature("WComm (Komunitas)", "waste_community", Icons.Default.AccountCircle),
        Feature("WNews (Berita Lingkungan)", "wnews", Icons.Default.Newspaper),
        Feature("Profil Pengguna", "profil", Icons.Default.Person),
        Feature("Notifikasi", "notification", Icons.Default.Notifications)
    )
}