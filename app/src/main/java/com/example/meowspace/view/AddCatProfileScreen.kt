package com.example.meowspace.view

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.meowspace.data.UserRepository
import com.example.meowspace.service.RetrofitInstance
import com.example.meowspace.service.TokenManager
import com.example.meowspace.view_model.PostCatProfileViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCatProfile(navController: NavController, context: Context) {

    val viewModel = remember {
        val tokenManager = TokenManager(context)
        val repository = UserRepository(
            api = RetrofitInstance.userService,
            tokenManager = tokenManager,
            context = context
        )
        PostCatProfileViewModel(repository)
    }

    val name by viewModel.name.observeAsState("")
    val username by viewModel.username.observeAsState("")
    val birthDate by viewModel.birthDate.observeAsState("")
    val gender by viewModel.gender.observeAsState("male")
    val breed by viewModel.breed.observeAsState("")
    val photoFile by viewModel.photoFile.observeAsState(null)
    val uploadState by viewModel.uploadResult.observeAsState()
    val errorMessage = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(uploadState) {
        uploadState?.let { result ->
            result
                .onSuccess {
                    navController.navigate("catprofile") {
                        popUpTo("catprofile") { inclusive = true }
                    }
                }
                .onFailure {
                    Log.e("UploadCat", "Upload gagal", it)
                    errorMessage.value = it.localizedMessage ?: "Unknown error"
                }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val file = uriToFile(context, it)
            viewModel.photoFile.value = file
        }
    }

    val datePickerState = rememberDatePickerState()
    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(Date(millis))
                        viewModel.birthDate.value = formatted
                        showDatePicker.value = false
                    }
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        ) {
            if (photoFile != null) {
                Image(
                    painter = rememberAsyncImagePainter(photoFile!!.toUri()),
                    contentDescription = "Cat Photo",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Cat Icon",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp).offset(x = (-36).dp, y = (-36).dp)
                )
                IconButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .offset(x = (-30).dp, y = (-36).dp)
                        .background(Color.Cyan, CircleShape)
                        .size(24.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Image", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    showDatePicker.value = true
                }
        ) {
            OutlinedTextField(
                value = birthDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Birthday") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Date")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {},
                enabled = false,
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = breed,
            onValueChange = { viewModel.breed.value = it },
            label = { Text("Breed") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Meow Gender", modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            GenderButton("Male", gender == "male") {
                viewModel.gender.value = "male"
            }
            GenderButton("Female", gender == "female") {
                viewModel.gender.value = "female"
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.uploadCat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A00))
        ) {
            Text("Add Meow Profile", color = Color.White)
        }
    }
}

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File.createTempFile("upload", ".jpg", context.cacheDir)
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return file
}

@Composable
fun GenderButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFFFF8A00) else Color.White
        )
    ) {
        Text(text = text, color = if (selected) Color.White else Color.Black)
    }
}