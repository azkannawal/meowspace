package com.example.meowspace.view

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Gif
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.meowspace.data.UserRepository
import com.example.meowspace.service.RetrofitInstance
import com.example.meowspace.service.TokenManager
import com.example.meowspace.view_model.PostFeedViewModel
import java.io.File

@Composable
fun PostFeedScreen(navController: NavController, context: Context) {
    val viewModel = remember {
        val tokenManager = TokenManager(context)
        val repository = UserRepository(
            api = RetrofitInstance.userService,
            tokenManager = tokenManager,
            context = context
        )
        PostFeedViewModel(repository)
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val file = uriToFile(uri, context)
            viewModel.selectedImageFile = file
        }
    }

    var content by remember { mutableStateOf("") }

    val postResult by viewModel.postResult.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00CFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cancel",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Draft", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Post",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF00CFFF))
                            .clickable {
                                viewModel.postStatus(content)
                            }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                if (isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("What's happening...") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                viewModel.selectedImageFile?.let { file ->
                    Image(
                        painter = rememberAsyncImagePainter(file.toUri()),
                        contentDescription = "Cat Photo",
                        modifier = Modifier.fillMaxSize()
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    imagePickerLauncher.launch("image/*")
                }) {
                    Icon(Icons.Default.Image, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pilih Gambar")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Everyone can reply", color = Color(0xFF00CFFF), fontSize = 14.sp)

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Default.TextFields, contentDescription = null, tint = Color(0xFF00CFFF))
                    Icon(Icons.Default.Image, contentDescription = null, tint = Color(0xFF00CFFF))
                    Icon(Icons.Default.Brush, contentDescription = null, tint = Color(0xFF00CFFF))
                    Icon(Icons.Default.LiveTv, contentDescription = null, tint = Color(0xFF00CFFF))
                    Icon(Icons.Default.Gif, contentDescription = null, tint = Color(0xFF00CFFF))
                    Icon(Icons.Default.MoreHoriz, contentDescription = null, tint = Color(0xFF00CFFF))
                }

                postResult?.let {
                    LaunchedEffect(Unit) {
                        navController.navigate("feed") {
                            popUpTo("addCat") { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

fun uriToFile(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)!!
    val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return tempFile
}

