package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowspace.R

@Composable
fun FeedScreen(navController: NavController, context: Context = LocalContext.current) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FCFF)),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StorySection()
        }

        items(4) { index ->
            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                PostCard(
                    username = "Miko_kucing_jaksel",
                    age = "3 years old",
                    breed = "Mainecoon",
                    caption = "Miko hari ini jalan sore dulu 🐾😻 btw tadi ketemu kucing oren dia berantem WKWKWKW bener2 dah",
                    imageList = listOf(R.drawable.frame_51, R.drawable.frame_51),
                    likeCount = "6.2k",
                    commentCount = "2.1k",
                    shareCount = "346"
                )
            }
        }
    }
}


@Composable
fun StorySection() {
    val names = listOf("You", "Mikothecat", "Kucingoren", "Morganisa_", "Kitty", "Rock", "More")
    LazyRow(
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(names) { name ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.LightGray, CircleShape)
                        .background(Color.White)
                        .padding(2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.frame_36),
                        contentDescription = "Story",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                Text(
                    text = name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PostCard(
    username: String,
    age: String,
    breed: String,
    caption: String,
    imageList: List<Int>,
    likeCount: String,
    commentCount: String,
    shareCount: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFCCF2FF), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.frame_36),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(username, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoChip("Male", Color(0xFF1E3A5F))
                    InfoChip(age, Color(0xFFFFA63A))
                    InfoChip(breed, Color(0xFF1E3A5F))
                }
            }
        }

        Text(
            text = caption,
            modifier = Modifier.padding(top = 12.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            imageList.forEach {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InteractionIcon(Icons.Default.Pets, likeCount)
            InteractionIcon(Icons.Default.ChatBubble, commentCount)
            InteractionIcon(Icons.Default.Bookmark, shareCount)
        }
    }
}

@Composable
fun InfoChip(text: String, bgColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 8.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 10.sp)
    }
}

@Composable
fun InteractionIcon(icon: ImageVector, count: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.DarkGray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = count, fontSize = 12.sp, color = Color.DarkGray)
    }
}
