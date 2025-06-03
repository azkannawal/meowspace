package com.example.meowspace.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.meowspace.R
import com.example.meowspace.data.UserRepository
import com.example.meowspace.model.MidtransRequest
import com.example.meowspace.service.RetrofitInstance
import com.example.meowspace.service.TokenManager
import com.example.meowspace.view_model.CatProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun BookHealthScreen(navController: NavController, context: Context) {
    var selectedCat by remember { mutableStateOf<String?>(null) }

    val catViewModel = remember {
        val tokenManager = TokenManager(context)
        val repository = UserRepository(
            api = RetrofitInstance.userService,
            tokenManager = tokenManager,
            context = context
        )
        CatProfileViewModel(repository)
    }

    val catResponse by catViewModel.catResult.observeAsState()

    LaunchedEffect(Unit) {
        catViewModel.loadCatProfile()
    }

    val cat = catResponse?.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.8f), shape = CircleShape)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.rectangle_76),
                        contentDescription = "Doctor Photo",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Dr. Dewi Rahma", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Veterinary Cardiologist", color = Color(0xFFFF7D00))
                        Text("2 Years of Experience", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Rp.", fontSize = 12.sp)
                        Text("89k", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Alumnus", fontWeight = FontWeight.SemiBold)
                        Text("Univertas Brawijaya, 2025", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Place, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Praktik di", fontWeight = FontWeight.SemiBold)
                        Text("RSUB, Malang", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF00CFFF))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Nomor STR", fontWeight = FontWeight.SemiBold)
                        Text("656527623454254554", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Choose Your Meow",
            color = Color(0xFF00A3FF),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cat?.name != null){
        CatSelector(cat.name, cat.photoUrl, selectedCat == cat.name) {
            selectedCat = if (selectedCat == cat.name) null else cat.name
        }
        }else{
            Text("Add your cat first", fontWeight = FontWeight.Bold)
        }

//        Spacer(modifier = Modifier.height(8.dp))
//        CatSelector("Kiehl Anak Kelo", R.drawable.frame_36, selectedCat == "Kiehl Anak Kelo") {
//            selectedCat = if (selectedCat == "Kiehl Anak Kelo") null else "Kiehl Anak Kelo"
//        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val orderId = UUID.randomUUID().toString()
                val paymentRequest = MidtransRequest(
                    orderId = orderId,
                    grossAmount = 89000,
                    fullName = "Jane Doe",
                    email = "janedoe@gmail.com"
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitInstance.userService.createTransaction(paymentRequest)
                        withContext(Dispatchers.Main) {
                            navController.navigate("payment/${response.snapToken}")
                        }
                    } catch (e: Exception) {
                        Log.e("Midtrans", "Gagal buat transaksi: ${e.message}")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7C00))
        ) {
        Text("Book Now", color = Color.White)
    }


    }
}

@Composable
fun CatSelector(name: String, photoRes: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (selected) Color(0xFFFF8C2F) else Color(0xFFF5F5F5)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = photoRes,
            contentDescription = "Cover Photo",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(name, fontWeight = FontWeight.Bold)
    }
}
