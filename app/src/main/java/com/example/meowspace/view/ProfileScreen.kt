package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, context: Context = LocalContext.current) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
        )
        Box(
            modifier = Modifier
                .offset(y = (-40).dp)
                .size(80.dp)
                .background(Color.White, shape = CircleShape)
                .border(2.dp, Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                tint = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
        }
        Text("Kiehl", style = MaterialTheme.typography.titleLarge)
        Text("@kiehl_anak_kelo", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("0", style = MaterialTheme.typography.titleMedium)
                Text("Followers", style = MaterialTheme.typography.labelSmall)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("1", style = MaterialTheme.typography.titleMedium)
                Text("Following", style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 16.dp)) {
            InfoTag("Male")
            InfoTag("1 y.o")
            InfoTag("Persian")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Hi paw lovers! my name is Kiehl... aku anak Kelo",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        // Filter chips
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip("All", selected = true)
            FilterChip("Photos")
            FilterChip("Videos")
        }
        // Grid dummy content
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(9) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color(0xFFB3E5FC), shape = RoundedCornerShape(16.dp))
                )
            }
        }
    }
}

@Composable
fun InfoTag(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFF90A4AE), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text, color = Color.White, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun FilterChip(text: String, selected: Boolean = false) {
    Box(
        modifier = Modifier
            .background(
                color = if (selected) Color(0xFFFFA726) else Color(0xFFB3E5FC),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}
