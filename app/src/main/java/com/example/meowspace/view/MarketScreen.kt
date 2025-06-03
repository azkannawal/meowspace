package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowspace.R

@Composable
fun MarketScreen(navController: NavController, context: Context = LocalContext.current) {

    val banner = listOf(
        R.drawable.frame_50, R.drawable.frame_50, R.drawable.frame_50,
        R.drawable.frame_50, R.drawable.frame_50
    )

    val categories = listOf(
        R.drawable.frame_48, R.drawable.frame_49,
        R.drawable.frame_48, R.drawable.frame_49,
        R.drawable.frame_48,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // Search bar & icons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.FavoriteBorder, contentDescription = null, modifier = Modifier.size(24.dp))
        }

        // Banner
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            itemsIndexed(banner) { index, bannerResId ->
                Image(
                    painter = painterResource(id = bannerResId),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = if (index != banner.lastIndex) 12.dp else 0.dp)
                        .size(140.dp)
                )
            }
        }

        // Top Categories Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Top Categories", style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp))
            Text("See All", color = Color.Blue, fontSize = 14.sp)
        }

        // Categories
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { imageResId ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .shadow(2.dp, RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        // Product Grid Title
        Text(
            "Recommended Products",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .height(900.dp)
                .padding(horizontal = 24.dp)
        ) {
            items(8) {
                Card(
                    modifier = Modifier.fillMaxWidth().clickable{navController.navigate("detail")},
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.frame_47),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)) {
                            Text("Cat Bed", style = MaterialTheme.typography.bodyLarge)
                            Text("Rp 159k", color = Color.Gray)
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
