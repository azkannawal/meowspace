package com.example.meowspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.meowspace.view.RegisterScreen
import com.example.meowspace.view.LoginScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meowspace.view.HomeScreen
import com.example.meowspace.view.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "welcome") {
                composable("welcome") {
                    WelcomeScreen(
                        onNavigateToLogin = { navController.navigate("login") },
                        onNavigateToRegister = { navController.navigate("register") }
                    )
                }
                composable("login") {
                    val context = LocalContext.current
                    LoginScreen(
                        context = context,
                        onNavigateToRegister = { navController.navigate("register") },
                        onLoginSuccess = { navController.navigate("home") {
                            // Pastikan halaman login tidak bisa diakses kembali
                            popUpTo("login") { inclusive = true }
                        } }
                    )
                }
                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = { navController.popBackStack() }
                    )
                }
                composable("home") {
                    HomeScreen()
                }
            }

        }
    }
}

