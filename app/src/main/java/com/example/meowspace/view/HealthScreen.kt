package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowspace.R


@Composable
fun HealthScreen(navController: NavController, context: Context = LocalContext.current) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.meowspace_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Kelo the Cat", fontWeight = FontWeight.Bold)
                Text("Malang, Indonesia", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            "Konsultasi Kesehatan Kucing",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF204387)
        )
        Text(
            "Rekomendasi Dokter",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF204387)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Doctor List
        LazyColumn {
            items(3) {
                DoctorCard()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DoctorCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.meowspace_logo),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text("Dr. Rina Masturina", fontWeight = FontWeight.Bold)
            Text("Veterinary Cardiologist", color = Color(0xFFFF6600), fontSize = 12.sp)
            Text("6 Years of Experience", fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Rp. 89k", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { /* TODO: handle booking */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD8EFFF)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Book Now!", color = Color.Black)
        }
    }
}
