package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowspace.R
import com.example.meowspace.service.TokenManager
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController, context: Context = LocalContext.current) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            HeaderSection()
            SearchBar()
            FeatureIcons()
            OnlineConsultationCard()
            CommunitySection()
            Button(
                onClick = {
                    // Hapus token saat logout
                    TokenManager(context).clearToken()

                    // Navigasi ke login atau welcome
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Text("Logout")
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
}

// ---------------- HEADER ----------------

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFA726))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Hi, Kelo the Cat!",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
            )
            Text("Add your Meow profile ->", color = Color.White)
        }
        Image(
            painter = painterResource(id = R.drawable.frame_36),
            contentDescription = "Profile",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .align(Alignment.TopEnd)
        )
    }
}

// ---------------- SEARCH BAR ----------------

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Cari produk, artikel, dokter ...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

// ---------------- FEATURE ICONS ----------------

@Composable
fun FeatureIcons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FeatureItem("Meow Feed", "frame_38")
        FeatureItem("Meow Health", "frame_39")
        FeatureItem("Meow Market", "frame_40")
    }
}

@Composable
fun FeatureItem(label: String, image: String) {
    val context = LocalContext.current
    val imageRes = remember(image) {
        context.resources.getIdentifier(image, "drawable", context.packageName)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0F7FA)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = label,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(label, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

// ---------------- CONSULTATION CARD ----------------

@Composable
fun OnlineConsultationCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame_37),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Online Vet Consultation", fontWeight = FontWeight.Bold)
                Text("Chat with a veterinarian now!", fontSize = 12.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC))
            ) {
                Text("CHAT NOW")
            }
        }
    }
}

// ---------------- COMMUNITY SECTION ----------------

@Composable
fun CommunitySection() {
    Text(
        "Meow Community",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        CommunityCard(
            title = "Apa itu Steril Kucing?",
            subtitle = "Syarat, Prosedur, Manfaat, dan Harganya!",
            backgroundColor = Color(0xFFFFEBEE)
        )
        CommunityCard(
            title = "Pentingnya Vaksinasi pada Kucing!",
            subtitle = "Jenis, Tujuan, dan Manfaatnya!",
            backgroundColor = Color(0xFFE1F5FE)
        )
    }
}

@Composable
fun CommunityCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(180.dp) // Sesuaikan lebar
            .height(200.dp), // Tinggi tetap agar seimbang
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.weight(1f)) // Mendorong tombol ke bawah
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB))
            ) {
                Text("See More", color = Color.Black)
            }
        }
    }
}
