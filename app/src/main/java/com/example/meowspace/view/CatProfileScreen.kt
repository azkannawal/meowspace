package com.example.meowspace.view


import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.meowspace.data.UserRepository
import com.example.meowspace.service.RetrofitInstance
import com.example.meowspace.service.TokenManager
import com.example.meowspace.view_model.CatProfileViewModel
import com.example.meowspace.view_model.FeedViewModel
import java.time.LocalDate
import java.time.Period

@Composable
fun CatProfileScreen(navController: NavController, context: Context) {

    val catViewModel = remember {
        val tokenManager = TokenManager(context)
        val repository = UserRepository(
            api = RetrofitInstance.userService,
            tokenManager = tokenManager,
            context = context
        )
        CatProfileViewModel(repository)
    }

    val feedViewModel = remember {
        val tokenManager = TokenManager(context)
        val repository = UserRepository(
            api = RetrofitInstance.userService,
            tokenManager = tokenManager,
            context = context
        )
        FeedViewModel(repository)
    }

    val catResponse by catViewModel.catResult.observeAsState()
    val postList by feedViewModel.postResult.observeAsState()
    val isCatLoading by catViewModel.isLoading.observeAsState(false)
    val isFeedLoading by feedViewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        catViewModel.loadCatProfile()
        feedViewModel.loadFeed()
    }

    val cat = catResponse?.firstOrNull()
    val catUserId = catResponse?.firstOrNull()?.userId
    val filteredPosts = if (catUserId != null) {
        postList.orEmpty().filter {
            it.userId == catUserId && !it.photoUrl.isNullOrBlank()
        }
    } else {
        emptyList()
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cover Photo
            if (cat?.photoUrl != null) {
                AsyncImage(
                    model = cat.photoUrl,
                    contentDescription = "Cover Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color(0xFFE0E0E0))
                )
            }

            // Profile photo
            Box(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .size(80.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (cat?.photoUrl != null) {
                    AsyncImage(
                        model = cat.photoUrl,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(96.dp)
                            .background(Color.LightGray, shape = CircleShape)
                            .padding(24.dp)
                    )
                }

                if (cat == null) {
                    IconButton(
                        onClick = { navController.navigate("addcatprofile") },
                        modifier = Modifier
                            .offset(y = 50.dp)
                            .size(20.dp)
                            .background(Color.Cyan, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Text(cat?.name ?: "Unknown", style = MaterialTheme.typography.titleLarge)
            Text("@${cat?.username ?: "no_username"}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

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

            if (cat != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    InfoTag(cat.gender)
                    InfoTag(calculateAge(cat.birthDate))
                    InfoTag(cat.breed)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Hi paw lovers! my name is ${cat.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip("All", selected = true)
                FilterChip("Photos")
                FilterChip("Videos")
            }

            // Post Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPosts) { post ->
                    if (!post.photoUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = post.photoUrl,
                            contentDescription = "Post Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(Color.White.copy(alpha = 0.8f), shape = CircleShape)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}

fun calculateAge(birthDateStr: String): String {
    val birthDate = LocalDate.parse(birthDateStr)
    val today = LocalDate.now()
    val period = Period.between(birthDate, today)

    return when {
        period.years >= 1 -> "${period.years} year${if (period.years > 1) "s" else ""} old"
        period.months >= 1 -> "${period.months} month${if (period.months > 1) "s" else ""} old"
        else -> {
            val days = java.time.temporal.ChronoUnit.DAYS.between(birthDate, today)
            "$days day${if (days > 1) "s" else ""} old"
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
