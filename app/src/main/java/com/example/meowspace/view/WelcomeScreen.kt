package com.example.meowspace.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meowspace.R

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background kucing
        CatBackgroundImages()

        // Konten utama
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            LogoSection()
            Spacer(modifier = Modifier.height(20.dp))
            ButtonsSection(
                onSignInClick = onNavigateToLogin,
                onNewAccountClick = onNavigateToRegister
            )
        }
    }
}

@Composable
fun CatBackgroundImages() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Ganti R.drawable.cat1 dst. dengan gambar kucing yang sesuai
//        Image(
//            painter = painterResource(id = R.drawable.cat_1),
//            contentDescription = null,
//            modifier = Modifier
//                .size(100.dp)
//                .align(Alignment.TopStart)
//        )
//
//        Image(
//            painter = painterResource(id = R.drawable.cat_1),
//            contentDescription = null,
//            modifier = Modifier
//                .size(100.dp)
//                .align(Alignment.TopEnd)
//        )

        // Tambahkan lebih banyak kucing sesuai layout desain
    }
}

@Composable
fun LogoSection() {
    Image(
        painter = painterResource(id = R.drawable.meowspace_logo_blue),
        contentDescription = "Logo Meow Space",
        modifier = Modifier.size(280.dp)
    )
}

@Composable
fun ButtonsSection(
    onSignInClick: () -> Unit,
    onNewAccountClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Sign In", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onNewAccountClick,
            shape = RoundedCornerShape(50),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2)) // dua warna biru
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("New Account", fontSize = 16.sp, color = Color(0xFF2196F3))
        }
    }
}
