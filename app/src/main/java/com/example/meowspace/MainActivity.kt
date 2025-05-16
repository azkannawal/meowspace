package com.example.meowspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.meowspace.view.RegisterScreen
import com.example.meowspace.view.LoginScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.meowspace.ui.theme.MeowSpaceTheme
import com.example.meowspace.view.HomeScreen
import com.example.meowspace.view.ProfileScreen
import com.example.meowspace.view.SplashScreen
import com.example.meowspace.view.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.runtime.getValue
import com.example.meowspace.view.FeedScreen
import com.example.meowspace.view.HealthScreen
import com.example.meowspace.view.MarketScreen

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()

        setContent {
            MeowSpaceTheme {
                val navController = rememberAnimatedNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route
                val showBottomBar = currentRoute in listOf("home", "profile", "feed", "market", "health")

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = {
                            slideInVertically(initialOffsetY = { 1000 }) + fadeIn(animationSpec = tween(100))
                        },
                        exitTransition = {
                            slideOutVertically(targetOffsetY = { 1000 }) + fadeOut(animationSpec = tween(100))
                        },
                        popEnterTransition = {
                            slideInVertically(initialOffsetY = { -1000 }) + fadeIn(animationSpec = tween(100))
                        },
                        popExitTransition = {
                            slideOutVertically(targetOffsetY = { -1000 }) + fadeOut(animationSpec = tween(100))
                        }
                    ) {
                        composable("splash") {
                            val context = LocalContext.current
                            SplashScreen(navController, context)
                        }
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
                                onLoginSuccess = {
                                    navController.navigate("home") {
                                        popUpTo(0)
                                        launchSingleTop = true
                                    }
                                }
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
                            val context = LocalContext.current
                            HomeScreen(navController, context)
                        }
                        composable("profile") {
                            val context = LocalContext.current
                            ProfileScreen(navController, context)
                        }
                        composable("feed") {
                            val context = LocalContext.current
                            FeedScreen(navController, context)
                        }
                        composable("market") {
                            val context = LocalContext.current
                            MarketScreen(navController, context)
                        }
                        composable("health") {
                            val context = LocalContext.current
                            HealthScreen(navController, context)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocalHospital, contentDescription = null) },
            selected = currentRoute == "health",
            onClick = {
                if (currentRoute != "health") {
                    navController.navigate("health") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Pets, contentDescription = "Feed") },
            selected = currentRoute == "feed",
            onClick = {
                if (currentRoute != "feed") {
                    navController.navigate("feed") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingBasket, contentDescription = null) },
            selected = currentRoute == "market",
            onClick = {
                if (currentRoute != "market") {
                    navController.navigate("market") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            selected = currentRoute == "profile",
            onClick = {
                if (currentRoute != "profile") {
                    navController.navigate("profile") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}



