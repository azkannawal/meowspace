package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.meowspace.service.TokenManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, context: Context) {
    LaunchedEffect(Unit) {
        delay(300)
        val token = TokenManager(context).getToken()
        if (token != null) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("welcome") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00CFFF)) // Latar belakang biru polos
    )
}
