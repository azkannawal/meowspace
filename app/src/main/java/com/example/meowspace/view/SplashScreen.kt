package com.example.meowspace.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.meowspace.service.TokenManager

@Composable
fun SplashScreen(navController: NavController, context: Context) {
    LaunchedEffect(Unit) {
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
}

