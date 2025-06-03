package com.example.meowspace.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.meowspace.R
import com.example.meowspace.model.MidtransRequest
import com.example.meowspace.service.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun ProductDetailScreen(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomBar(
                onBuyClicked = { showPopup = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            // Gambar & Icon Atas
            Box {
                Image(
                    painter = painterResource(id = R.drawable.frame_36),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { navController.popBackStack() },
                        tint = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        modifier = Modifier.size(28.dp),
                        tint = Color.White
                    )
                }
            }

            // Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(7) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(2.dp)
                            .background(
                                if (it == 0) Color.Black else Color.Gray.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Detail Produk
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Cat Bed - Premium...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("4.7", fontSize = 14.sp)
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text("7.5k", fontSize = 14.sp, color = Color.Gray)
                    }
                }

                Text(
                    text = "Rp 159.000",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6600),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text("4.6k Sold", color = Color.Gray, fontSize = 13.sp)
                    Text("2.9k Reviews", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(16.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF7F7F7))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.meowspace_logo_blue),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("Toko Kucing.Id", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "Jl. Mawar No. 12A, Kecamatan Lowokwaru, Malang",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Deskripsi Produk
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Tempat tidur kucing super empuk dan hangat yang dirancang khusus untuk kenyamanan maksimal si meong! Terbuat dari bahan fleece berkualitas tinggi yang lembut, anti alergi, dan mudah dicuci.",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(Modifier.height(8.dp))

                Text("Fitur Utama:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))

                val fitur = listOf(
                    "Material: Premium fleece + anti-slip bottom",
                    "Ukuran: Diameter 45 cm (muat untuk kucing dewasa)",
                    "Warna: Soft Grey, Beige, Pink",
                    "Cocok untuk: Kucing semua umur & ukuran kecil-menengah",
                    "Bisa dicuci mesin atau manual",
                    "Ringan dan mudah dipindah..."
                )

                fitur.forEach {
                    Text("• $it", fontSize = 13.sp, color = Color.DarkGray)
                }

                if (isExpanded) {
                    Spacer(Modifier.height(12.dp))
                    Text("Manfaat:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(Modifier.height(4.dp))
                    val manfaat = listOf(
                        "Membantu kucing merasa aman dan rileks",
                        "Ideal untuk tidur panjang atau sekadar rebahan santai",
                        "Bikin spot tidur kucing lo makin estetik dan cozy"
                    )
                    manfaat.forEach {
                        Text("• $it", fontSize = 13.sp, color = Color.DarkGray)
                    }

                    Spacer(Modifier.height(8.dp))

                    Text("Harga:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Rp 159.000", fontSize = 13.sp, color = Color.Black)

                    Spacer(Modifier.height(8.dp))

                    Text("Ketersediaan:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        "✔ Tersedia – Siap dikirim dalam 1–2 hari kerja",
                        fontSize = 13.sp,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(8.dp))

                    Text("Catatan Tambahan:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        "✨ Bonus mainan kecil berbentuk tikus untuk setiap pembelian (selama persediaan masih ada)",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text(
                        text = if (isExpanded) "Tutup" else "Continue Reading...",
                        color = Color.Black
                    )
                }
                Spacer(Modifier.height(80.dp))
            }

        }
        AnimatedVisibility(visible = showPopup, enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200))) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000)) // semi transparan background
                    .clickable { showPopup = false } // close jika klik di luar
            ) {
                // Bottom sheet container
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(24.dp)
                ) {
                    VariantSelector(navController)
                }
            }
        }
    }
}

@Composable
fun VariantSelector(navController: NavController) {
    var selectedVariant by remember { mutableStateOf("Comfy Brown") }
    var quantity by remember { mutableStateOf(1) }
    val variants = listOf("Royal Red", "Casual Grey", "Furry Blue", "Comfy Brown")

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.85f)
            .fillMaxWidth()
    ) {
        item { Text("Pilih Varian", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
        item { Spacer(Modifier.height(12.dp)) }

        variants.chunked(2).forEach { rowVariants ->
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowVariants.forEach { variant ->
                        val isSelected = variant == selectedVariant
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) Color(0xFF5C9EFF) else Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                                .background(if (isSelected) Color(0xFFE0F0FF) else Color.White)
                                .clickable { selectedVariant = variant }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(variant, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
            Text("Quantity", fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3F3F3))
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text(quantity.toString(), modifier = Modifier.padding(horizontal = 16.dp))

                IconButton(onClick = { quantity++ }) {
                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    val orderId = UUID.randomUUID().toString()
                    val paymentRequest = MidtransRequest(
                        orderId = orderId,
                        grossAmount = 150000*quantity,
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
                Text("Confirm Purchases", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier,  onBuyClicked: () -> Unit = {}  ) {
    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Default.ChatBubbleOutline, contentDescription = "Chat")
                Divider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp),
                    color = Color.LightGray
                )
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                Divider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp),
                    color = Color.LightGray
                )
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onBuyClicked() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C5C86))
            ) {
                Text("Buy Now!", color = Color.White)
            }
        }
    }
}
