package com.example.ep4_1.Views

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.ep4_1.R
import com.example.ep4_1.viewmodel.UserViewModel

@Composable
fun barraInferior(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        contentColor = MaterialTheme.colorScheme.surface,
        actions = {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
                IconButton(onClick = { navController.navigate("usuarios") }) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraSuperiorHome(navController: NavHostController) {
    TopAppBar(
        modifier = Modifier,
        title = { Text("Nura") },
        navigationIcon = {},
        actions = {
            Image(
                painter = painterResource(id = R.drawable.usericon),
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(54.dp)
                    .align(Alignment.CenterVertically)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.primary_600),
            titleContentColor = colorResource(id = R.color.black)
        )
    )
}

@Composable
fun HomeView(navController: NavHostController, username: String?, email: String?) {
    Scaffold (
        topBar = { barraSuperiorHome(navController)},
        content = {reservado ->
            Surface(modifier = Modifier.padding(reservado)){
                HomeViewContent(navController, username, email)
            }
        },
        bottomBar = { barraInferior(navController)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewContent(navController: NavHostController, username: String?, email: String?) {
    val userViewModel: UserViewModel = viewModel()
    val loggedUser by userViewModel.loggedUser.collectAsState()

    LaunchedEffect(loggedUser) {
        Log.d("HomeViewContent", "loggedUser: $loggedUser")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "Bienvenido a Nura",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                color = Color.Black
            )
            Text(
                text = "$username",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Nura es una aplicación de gestión de usuarios. Aquí puedes gestionar tus usuarios, editar sus detalles y mucho más.",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Correo electrónico: $email",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
            Text(
                "Fecha de registro: 01/01/2023",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /*TODO: Navigate to user profile*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.black)
                ),
            ) {
                Text("Ver perfil")
            }
        }
    }
}
