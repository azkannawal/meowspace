package com.example.meowspace.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meowspace.view_model.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
//    val isLoading by viewModel.isLoading
//    val errorMessage by viewModel.errorMessage

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Login")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

//        if (errorMessage.isNotEmpty()) {
//            Text(text = errorMessage, color = Color.Red)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//
//        Button(
//            onClick = { viewModel.login(onLoginSuccess) },
//            modifier = Modifier.fillMaxWidth(),
//            enabled = !isLoading
//        ) {
//            Text(if (isLoading) "Loading..." else "Login")
//        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Belum punya akun? Daftar di sini")
        }
    }
}
