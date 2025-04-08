//package com.supra.authentication
package com.example.meowspace


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meowspace.ui.theme.MeowSpaceTheme


import android.util.Patterns
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.VisualTransformation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterScreen()
                }
            }
        }
    }
}

@Composable
fun RegisterScreen() {
    val fullName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val fullNameError = remember { mutableStateOf<String?>(null) }
    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val confirmPasswordError = remember { mutableStateOf<String?>(null) }

    val showPassword = remember { mutableStateOf(false) }
    val showConfirmPassword = remember { mutableStateOf(false) }

    val isConfirmPasswordValid = confirmPassword.value.isNotEmpty() &&
            confirmPassword.value == password.value &&
            password.value.length >= 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = fullName.value,
            onValueChange = {
                fullName.value = it
                if (it.isNotBlank()) fullNameError.value = null
            },
            label = { Text("Full Name") },
            isError = fullNameError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(fullNameError.value)

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                if (Patterns.EMAIL_ADDRESS.matcher(it).matches()) emailError.value = null
            },
            label = { Text("Email") },
            isError = emailError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(emailError.value)

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                if (it.length >= 6) passwordError.value = null
            },
            label = { Text("Password") },
            isError = passwordError.value != null,
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (showPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(passwordError.value)

        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = {
                confirmPassword.value = it
                if (it == password.value) confirmPasswordError.value = null
            },
            label = { Text("Confirm Password") },
            isError = confirmPasswordError.value != null,
            visualTransformation = if (showConfirmPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (isConfirmPasswordValid) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Password match",
                        tint = Color.Green
                    )
                } else {
                    val image = if (showConfirmPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { showConfirmPassword.value = !showConfirmPassword.value }) {
                        Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        ErrorText(confirmPasswordError.value)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                validateForm(
                    fullName.value,
                    email.value,
                    password.value,
                    confirmPassword.value,
                    fullNameError,
                    emailError,
                    passwordError,
                    confirmPasswordError
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}

@Composable
fun ErrorText(errorMessage: String?) {
    if (!errorMessage.isNullOrEmpty()) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

private fun validateForm(
    fullName: String,
    email: String,
    password: String,
    confirmPassword: String,
    fullNameError: MutableState<String?>,
    emailError: MutableState<String?>,
    passwordError: MutableState<String?>,
    confirmPasswordError: MutableState<String?>
) {
    fullNameError.value = if (fullName.isBlank()) "Full name is required" else null

    emailError.value = when {
        email.isBlank() -> "Email is required"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email is invalid"
        else -> null
    }

    passwordError.value = when {
        password.isBlank() -> "Password is required"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }

    confirmPasswordError.value = when {
        confirmPassword.isBlank() -> "Confirm password is required"
        confirmPassword != password -> "Passwords do not match"
        else -> null
    }
}