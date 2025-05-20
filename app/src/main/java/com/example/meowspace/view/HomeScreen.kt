package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController, context: Context = LocalContext.current) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            HeaderSection(
                userName = "Kelo the Cat",
                userImage = painterResource(R.drawable.frame_36),
                onAddProfileClick = { /* action */ }
            )
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

@Composable
fun HeaderSection(
    userName: String = "Kelo the Cat",
    userImage: Painter,
    onAddProfileClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height

            val waveHeight = height * 0.17f

            val path = Path().apply {
                moveTo(0f, height - waveHeight)
                cubicTo(
                    width * 0.25f, height,
                    width * 0.75f, height - waveHeight * 2,
                    width, height - waveHeight
                )
                lineTo(width, 0f)
                lineTo(0f, 0f)
                close()
            }

            drawPath(path, color = Color(0xFFFF8C2F))
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hi, $userName!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Add your Meow profile ->",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { onAddProfileClick() }
                )
            }

            Image(
                painter = userImage,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
        }
    }
}

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
            .padding(bottom = 16.dp, start = 24.dp, end = 24.dp)
    )
}

@Composable
fun FeatureIcons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
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
                .size(80.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE0F7FA))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFF4FC3F7)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Text(label, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun OnlineConsultationCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        modifier = Modifier
            .padding( horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame_37a),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text("Online Vet Consultation", fontWeight = FontWeight.Bold)
                Text("Chat with a veterinarian now!", fontSize = 12.sp)
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4FC3F7)),
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                ) {
                    Text("CHAT NOW")
                }
            }

        }
    }
}

@Composable
fun CommunitySection() {
    Text(
        "Meow Community",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(start = 24.dp, top = 8.dp)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
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
            .width(180.dp)
            .height(200.dp),
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
