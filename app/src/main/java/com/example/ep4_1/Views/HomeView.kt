package com.example.ep4_1.Views

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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ep4_1.Models.User
import com.example.ep4_1.R

@Composable
fun barraInferior(navController: NavHostController, loggedUser: User) {
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


@Composable
fun HomeView(navController: NavHostController, loggedUser: User) {
    Scaffold (
        topBar = { barraSuperiorHome(navController)},
        content = {reservado ->
            Surface(modifier = Modifier.padding(reservado)){
                HomeViewContent(navController, loggedUser)
            }
        },
        bottomBar = { barraInferior(navController, loggedUser)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraSuperiorHome(navController: NavHostController) {
    TopAppBar(
        modifier = Modifier,
        title = { Text("Nura") },
        navigationIcon = {
        },
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
fun HomeViewContent(navController: NavHostController, loggedUser: User){
    val fullname = loggedUser.fullname
    val email = loggedUser.email
    Box(modifier = Modifier.fillMaxSize().padding(24.dp)){
        Column(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()){
                Text("Bienvenido a Nura",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp,
                    color = Color.Black
                )
                Text("$fullname",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text("Nura es una aplicación de gestión de usuarios. Aquí puedes gestionar tus usuarios, editar sus detalles y mucho más.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text("Correo electrónico: $email",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
                Text("Fecha de registro: 01/01/2023",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { /*TODO: Navigate to user profile*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.black)
                    ),
                    ){
                    Text("Ver perfil")
                }
            }
        }
    }
}