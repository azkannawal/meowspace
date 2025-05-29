package com.example.meowspace.view

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowspace.R
import com.example.meowspace.data.UserRepository
import com.example.meowspace.service.RetrofitInstance
import com.example.meowspace.service.TokenManager
import com.example.meowspace.view_model.ProfileViewModel

@Composable
fun HomeScreen(navController: NavController, context: Context) {

    val viewModel = remember {
        val tokenManager = TokenManager(context)
        val repository = UserRepository(
            api = RetrofitInstance.userService,
            tokenManager = tokenManager,
            context = context
        )
        ProfileViewModel(repository)
    }

    val userProfile by viewModel.userProfile.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    val userName = if (isLoading) "Loading..." else userProfile?.fullName ?: "Guest"

    Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            HeaderSection(
                userName = userName,
                userImage = painterResource(R.drawable.frame_36),
                onAddProfileClick = {
                    navController.navigate("catprofile")
                }
            )
            SearchBar()
            FeatureIcons(navController)
            OnlineConsultationCard(navController)
            CommunitySection()

            Spacer(modifier = Modifier.height(80.dp))
        }
}

@Composable
fun FeatureIcons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FeatureItem("Meow Feed", "frame_38") {
            navController.navigate("feed")
        }
        FeatureItem("Meow Health", "frame_39") {
            navController.navigate("health")
        }
        FeatureItem("Meow Market", "frame_40") {
            navController.navigate("market")
        }
    }
}

@Composable
fun HeaderSection(
    userName: String = "Kelo the Cat",
    userImage: Painter,
    onAddProfileClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height

            val waveHeight = height * 0.17f

            val path = Path().apply {
                moveTo(0f, height - waveHeight)
                cubicTo(
                    width * 0.25f, height,
                    width * 0.75f, height - waveHeight * 2,
                    width, height - waveHeight
                )
                lineTo(width, 0f)
                lineTo(0f, 0f)
                close()
            }

            drawPath(path, color = Color(0xFFFF8C2F))
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hi, $userName!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Go to Meow profile ->",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { onAddProfileClick() }
                )
            }

            Image(
                painter = userImage,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Cari produk, artikel, dokter, lainnya", fontSize = 14.sp ) },
        leadingIcon = { Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.padding(start = 12.dp)
        ) },
        shape = RoundedCornerShape(32.dp),
        textStyle = TextStyle(
            fontSize = 14.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 24.dp, end = 24.dp)
    )
}

@Composable
fun FeatureItem(label: String, image: String, onClick: () -> Unit = {}) {
    val context = LocalContext.current
    val imageRes = remember(image) {
        context.resources.getIdentifier(image, "drawable", context.packageName)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val rippleColor = Color(0xFFFFFFFF) // Ripple transparan halus

    Column(modifier = Modifier.clip(RoundedCornerShape(12.dp)) // biar ripple ada batasnya
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = rippleColor),
            onClick = onClick
        ), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE0F7FA))
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFF4FC3F7)),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Text(label, fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp), )
    }
}

@Composable
fun OnlineConsultationCard(navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        modifier = Modifier
            .padding( horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame_60),
                contentDescription = null,
                modifier = Modifier.size(110.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text("Online Vet Consultation", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = "Chat with a veterinarian now!",
                    fontSize = 12.sp,
                    color = Color(0xFFBD6B48)
                )
                Button(
                    onClick = {navController.navigate("health")},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4FC3F7)),
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                ) {
                    Text(
                        text = "Chat Now",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.sp,
                            color = Color(0xFFFAFAFA)
                        )
                    )
                }
            }

        }
    }
}

@Composable
fun CommunitySection() {
    Text(
        "Meow Community",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(start = 24.dp, top = 8.dp)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        CommunityCard(
            title = "Apa itu Steril Kucing?",
            subtitle = "Syarat, Prosedur, Manfaat, dan Harganya!",
            backgroundColor = Color(0xFFFFF3E0)
        )
        CommunityCard(
            title = "Pentingnya Vaksinasi pada Kucing!",
            subtitle = "Jenis, Tujuan, dan Manfaatnya!",
            backgroundColor = Color(0xFFFFF3E0)
        )
    }
}

@Composable
fun CommunityCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color(0xFFBD6B48)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4FC3F7))
            ) {
                Text("See More", fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
