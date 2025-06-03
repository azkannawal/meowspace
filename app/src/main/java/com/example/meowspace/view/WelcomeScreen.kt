package com.example.meowspace.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
            Text("Sign In", fontSize = 16.sp, color = Color.White)
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
