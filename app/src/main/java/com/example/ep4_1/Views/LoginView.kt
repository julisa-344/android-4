package com.example.ep4_1.Views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ep4_1.ApplicationEP4
import com.example.ep4_1.Models.User
import com.example.ep4_1.R
import com.example.ep4_1.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginView(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.primary_600))
    ) {
        Box(modifier = Modifier) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = colorResource(id = R.color.primary_600),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(start = 20.dp, top = 40.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_img),
                contentDescription = null,
                modifier = Modifier
                    .width(480.dp)
                    .height(270.dp)
                    .padding(top = 40.dp)
            )

            Text(
                text = "Inicia Sesión",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                color = colorResource(id = R.color.black),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 20.dp, bottom = 50.dp)
            )

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passVisible by remember { mutableStateOf(true) }

            val isEmailValid by derivedStateOf { android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() }
            val isPasswordValid by derivedStateOf { password.length >= 8 }
            val isFormValid by derivedStateOf { isEmailValid && isPasswordValid }

            Column(modifier = Modifier) {
                Text(text = "Correo", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(5.dp))

                Box {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(text = "example@example.com", style = MaterialTheme.typography.bodySmall) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = !isEmailValid,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(13.dp))
                    )
                }

                Spacer(modifier = Modifier.size(15.dp))

                Text(text = "Contraseña", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(5.dp))

                Box {
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text(text = "tu contraseña", style = MaterialTheme.typography.bodySmall) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            Icon(
                                imageVector = if (passVisible) Icons.Default.Lock else Icons.Default.Lock,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 15.dp)
                                    .clickable { passVisible = !passVisible }
                            )
                        },
                        visualTransformation = if (passVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        isError = !isPasswordValid,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(13.dp))
                    )
                }

                Text(
                    text = "Olvidé mi contraseña!",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp)
                )

                Spacer(modifier = Modifier.size(50.dp))
            }

            BtnEntrar(navController, isFormValid, email, password, userViewModel)

            Row {
                Text(text = "No tienes una cuenta?", style = MaterialTheme.typography.bodySmall, color = colorResource(id = R.color.black))
                Text(
                    text = " Registrate!",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.clickable { navController.navigate("register") }
                )
            }
        }
    }
}

@Composable
fun BtnEntrar(navController: NavController, isFormValid: Boolean, email: String, password: String, userViewModel: UserViewModel) {
    val db = ApplicationEP4.database
    val context = LocalContext.current
    Column(modifier = Modifier.padding(15.dp)) {
        Button(
            onClick = {
                if (!isFormValid) {
                    Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                CoroutineScope(Dispatchers.IO).launch {
                    val user = db.userDao().findUser(email, password)
                    if (user != null) {
                        withContext(Dispatchers.Main) {
                            navController.navigate("home")
                        }
                    } else {
                        // Show a toast message if the user is not found
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            enabled = isFormValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.black)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                "Entrar",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewComponente2() {
    val navController = rememberNavController()
    LoginView(navController = navController)
}
