package com.example.meowspace

import RegisterActivityViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.meowspace.ui.theme.MeowSpaceTheme
import androidx.lifecycle.ViewModelProvider
import com.example.meowspace.view_model.RegisterActivityViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meowspace.repository.AuthRepository
import com.example.meowspace.utils.APIConsumer
import com.example.meowspace.utils.APIService
import com.example.meowspace.view.RegisterScreen
import com.example.meowspace.view.LoginScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onNavigateToRegister = { navController.navigate("register") },
                        onLoginSuccess = { navController.navigate("register")}
                    )
                }
                composable("register") {
                    RegisterScreen(
                        onRegisterSuccess = {
                            navController.navigate("login") {
                            popUpTo("register") { inclusive = true } // Optional: hilangkan screen register dari stack
                            }
                        },
                        onNavigateToLogin = { navController.popBackStack() } // kembali ke login
                    )
                }
            }
        }
    }
}

