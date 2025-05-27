package com.example.meowspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.meowspace.view.RegisterScreen
import com.example.meowspace.view.LoginScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.meowspace.ui.theme.MeowSpaceTheme
import com.example.meowspace.view.HomeScreen
import com.example.meowspace.view.ProfileScreen
import com.example.meowspace.view.SplashScreen
import com.example.meowspace.view.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.meowspace.view.CatProfileScreen
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
                val authRoutes = listOf("login", "register", "welcome")


                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            CustomBottomBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = {
                            with(this) {
                                val from = initialState.destination.route
                                val to = targetState.destination.route
                                if (from in authRoutes && to in authRoutes) {
                                    slideInVertically(initialOffsetY = { 1000 }) + fadeIn(animationSpec = tween(100))
                                } else {
                                    fadeIn(animationSpec = tween(100))
                                }
                            }
                        },
                        exitTransition = {
                            with(this) {
                                val from = initialState.destination.route
                                val to = targetState.destination.route
                                if (from in authRoutes && to in authRoutes) {
                                    slideOutVertically(targetOffsetY = { 1000 }) + fadeOut(
                                        animationSpec = tween(100)
                                    )
                                } else {
                                    fadeOut(animationSpec = tween(100))
                                }
                            }
                        },
                        popEnterTransition = {
                            with(this) {
                                val from = initialState.destination.route
                                val to = targetState.destination.route
                                if (from in authRoutes && to in authRoutes) {
                                    slideInVertically(initialOffsetY = { -1000 }) + fadeIn(
                                        animationSpec = tween(100)
                                    )
                                } else {
                                    fadeIn(animationSpec = tween(100))
                                }
                            }
                        },
                        popExitTransition = {
                            with(this) {
                                val from = initialState.destination.route
                                val to = targetState.destination.route
                                if (from in authRoutes && to in authRoutes) {
                                    slideOutVertically(targetOffsetY = { -1000 }) + fadeOut(
                                        animationSpec = tween(100)
                                    )
                                } else {
                                    fadeOut(animationSpec = tween(100))
                                }
                            }
                        }
                    )
                    {
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
                        composable("catprofile") {
                            val context = LocalContext.current
                            CatProfileScreen(navController, context)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf("home", "health", "feed", "market", "profile")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(32.dp))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { screen ->
                val isSelected = currentRoute == screen
                val icon = when (screen) {
                    "home" -> Icons.Default.Home
                    "health" -> Icons.Default.LocalHospital
                    "feed" -> Icons.Default.Pets
                    "market" -> Icons.Default.ShoppingBasket
                    "profile" -> Icons.Default.Person
                    else -> Icons.Default.Home
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isSelected -> Color(0xFFD0F3FF) // biru muda
                                else -> Color(0xFFF0F0F0) // abu muda
                            }
                        )
                        .clickable {
                            if (!isSelected) {
                                navController.navigate(screen) {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = screen,
                        tint = if (isSelected) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}




