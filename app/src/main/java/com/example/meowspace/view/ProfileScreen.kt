package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.meowspace.R
import com.example.meowspace.service.TokenManager
import com.example.meowspace.view_model.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, context: Context? = null) {
    val safeContext = context ?: LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel()

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile(safeContext)
    }

    val userProfile = profileViewModel.userProfile
    val isLoading = profileViewModel.isLoading

    val userName = if (isLoading) "Loading..." else userProfile?.fullName ?: "Guest"
    val userRole = "Regular"
    val userImage = painterResource(id = R.drawable.meowspace_logo_blue)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color(0xFFFF8C2F))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 60.dp)
                ) {
                    Image(
                        painter = userImage,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                            .background(Color.White),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = userRole,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Account",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2A3E57)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ProfileOption(title = "Change Email")
                ProfileOption(title = "Change Password")
                ProfileOption(title = "Settings")
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Help Center",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2A3E57)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ProfileOption(title = "FAQ")
                ProfileOption(title = "Terms & Conditions")
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = {
                    TokenManager(safeContext).clearToken()
                    navController.navigate("welcome") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 24.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF80A0A))
            ) {
                Text(
                    text = "Log Out",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = Color(0xFFFAFAFA)
                    )
                )

            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
private fun ProfileOption(title: String) {
    Card(
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, Color(0xFF2A3E57)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(50.dp)
            .clickable { /* TODO */ },
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF2A3E57),
                    fontWeight = FontWeight.Medium
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF2A3E57)
            )
        }
    }
}
