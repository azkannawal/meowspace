package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowspace.R
import com.example.meowspace.ui.theme.LexendFont


@Composable
fun HealthScreen(navController: NavController, context: Context = LocalContext.current) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp) // supaya tidak mentok
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 24.dp, end = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.frame_36),
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
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Konsultasi Kesehatan Kucing",
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color(0xFF2E5E91)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rekomendasi Dokter",
                    fontFamily = FontFamily(Font(R.font.lexend_regular)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF1A2F4F)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Doctor List
        items(4) {
            DoctorCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun DoctorCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.rectangle_76),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp),
        )
        Column(modifier = Modifier.padding(end = 24.dp)){
            Text(
                "Dr. Rina Masturina",
                fontFamily = FontFamily(Font(R.font.lexend_regular)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color(0xFF1A2B41)
            )
            Text(
                "Veterinary Cardiologist",
                fontFamily = FontFamily(Font(R.font.lexend_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color(0xFFFF6600),
            )
            Text(
                "6 Years of Experience",
                fontFamily = FontFamily(Font(R.font.lexend_regular)),
                fontSize = 12.sp,
                color = Color(0xFFB0B0B0),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        "Rp.",
                        fontFamily = LexendFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1A2B41),
                    )
                    Row (verticalAlignment = Alignment.Bottom) {
                        Text(
                            "89",
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            color = Color(0xFF1A2B41),
                        )
                        Text(
                            "k",
                            fontFamily = LexendFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1A2B41),
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFD8EFFF))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        "Book Now",
                        fontFamily = LexendFont,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1A2B41),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}
