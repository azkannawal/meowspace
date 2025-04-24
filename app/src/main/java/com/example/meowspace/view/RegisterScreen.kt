package com.example.meowspace.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meowspace.view_model.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel(),
                   onRegisterSuccess: () -> Unit,
                   onNavigateToLogin: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = viewModel.name, onValueChange = { viewModel.name = it }, label = { Text("Nama") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = viewModel.email, onValueChange = { viewModel.email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (viewModel.passwordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = {
                    viewModel.passwordVisible = !viewModel.passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it },
            label = { Text("Konfirmasi Password") },
            visualTransformation = if (viewModel.confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (viewModel.confirmPasswordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = {
                    viewModel.confirmPasswordVisible = !viewModel.confirmPasswordVisible
                }) {
                    Icon(imageVector = image, contentDescription = "Toggle Confirm Password Visibility")
                }
            }
        )

        if (viewModel.errorMessage.isNotEmpty()) {
            Text(viewModel.errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { viewModel.register(onRegisterSuccess) },
            enabled = !viewModel.isLoading
        ) {
            Text("Daftar")
        }

        TextButton(onClick = { onNavigateToLogin() }) {
            Text("Sudah punya akun? Login di sini")
        }
    }
}
