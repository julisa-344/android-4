package com.example.ep4_1.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ep4_1.ApplicationEP4
import com.example.ep4_1.Models.User
import com.example.ep4_1.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.example.ep4_1.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterView(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    val userViewModel: UserViewModel = viewModel()
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.primary_600))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = colorResource(id = R.color.black),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp)
                .clickable { navController.popBackStack() }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = "Registrate",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                color = colorResource(id = R.color.black),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Username TextField
            TextFieldWithLabel(
                label = "Nombre de usuario",
                value = username,
                onValueChange = { username = it },
                placeholder = "usuario"
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Fullname TextField
            TextFieldWithLabel(
                label = "Nombre completo",
                value = fullname,
                onValueChange = { fullname = it },
                placeholder = "nombres"
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Email TextField
            TextFieldWithLabel(
                label = "Correo",
                value = email,
                onValueChange = { email = it },
                placeholder = "example@example.com",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Password TextField
            TextFieldWithLabel(
                label = "Contraseña",
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.length >= 8
                },
                placeholder = "tu contraseña",
                isPassword = true,
                isPasswordVisible = isPasswordVisible,
                onPasswordVisibilityChange = { isPasswordVisible = it }
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Register Button
            BtnRegistrarse(
                navController = navController,
                username = username,
                fullname = fullname,
                email = email,
                password = password,
                isButtonEnabled = username.isNotEmpty() && fullname.isNotEmpty() && email.isNotEmpty() && isPasswordValid,
                userViewModel = userViewModel
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Login prompt
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Tienes un cuenta?", style = MaterialTheme.typography.bodySmall, color = colorResource(id = R.color.black))
                Text(
                    text = " Logueate!",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.clickable { navController.navigate("login") }
                )
            }
        }
    }
}

@Composable
fun TextFieldWithLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityChange: ((Boolean) -> Unit)? = null
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, style = MaterialTheme.typography.bodySmall) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { onPasswordVisibilityChange?.invoke(!isPasswordVisible) }) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    }
                }
            } else null,
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(13.dp))
        )
    }
}

@Composable
fun BtnRegistrarse(
    navController: NavHostController,
    username: String,
    fullname: String,
    email: String,
    password: String,
    isButtonEnabled: Boolean,
    userViewModel: UserViewModel
) {
    val db = ApplicationEP4.database

    Column(
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
    ) {
        Button(
            onClick = {
                val uid = hashCode()
                val newUser = User(uid = uid, username = username, fullname = fullname, email = email, password = password)
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().insert(newUser)
                }
                userViewModel.setLoggedUser(newUser)
                navController.navigate("home")
            },
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrate", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComponente4() {
    val navController = rememberNavController()
    RegisterView(navController = navController)
}
