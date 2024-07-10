package com.example.ep4_1.Views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ep4_1.ApplicationEP4
import com.example.ep4_1.Models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ep4_1.R

@Composable
fun UsuariosView(navController: NavHostController, loggedUser: User) {
    Scaffold (
        topBar = {barraSuperiorUser(navController)},
        content = {reservado ->
            Surface(modifier = Modifier.padding(reservado)){
                ContentUsuariosView(navController, "")
            }
        },
        bottomBar = { barraInferior(navController)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun barraSuperiorUser(navController: NavHostController) {
    TopAppBar(
        modifier = Modifier,
        title = { Text("Lista de Usuarios") },
        navigationIcon = {
            IconButton(onClick = {navController.navigate("home")}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "retorno", tint = Color.Black)
            }
        },
        actions = {
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.primary_600),
            titleContentColor = colorResource(id = R.color.black)
        )
    )
}

@Composable
fun ContentUsuariosView(navController: NavHostController, fullname: String = "") {
    val db = ApplicationEP4.database
    val coroutineScope = rememberCoroutineScope()
    var users by remember { mutableStateOf(listOf<User>()) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = db) {
        coroutineScope.launch(Dispatchers.IO) {
            users = db.userDao().getAll()
        }
    }

    Scaffold(
        content = { reservado ->
            Surface(modifier = Modifier.padding(reservado)) {
                LazyColumn {
                    items(users) { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Username: ${user.username}",
                                style = MaterialTheme.typography.bodyMedium)
                            Row {
                                IconButton(onClick = {
                                    selectedUser = user
                                    isBottomSheetVisible = true
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                                }
                                IconButton(
                                    onClick = {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        db.userDao().delete(user)
                                        users = db.userDao().getAll()
                                    }
                                }) {
                                    Icon(Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        tint = colorResource(id = R.color.accent),
                                        )}
                            }
                        }
                    }
                }
            }
        }
    )

    if (isBottomSheetVisible && selectedUser != null) {
        EditUserBottomSheet(
            user = selectedUser!!,
            onDismiss = { isBottomSheetVisible = false },
            onSave = { updatedUser ->
                coroutineScope.launch(Dispatchers.IO) {
                    db.userDao().update(updatedUser)
                    users = db.userDao().getAll()
                }
                isBottomSheetVisible = false
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditUserBottomSheet(user: User, onDismiss: () -> Unit, onSave: (User) -> Unit) {
    var username by remember { mutableStateOf(user.username ?: "") }
    var email by remember { mutableStateOf(user.email ?: "") }
    var password by remember { mutableStateOf(user.password ?: "") }

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)

    ModalBottomSheetLayout(
        sheetBackgroundColor = colorResource(id = R.color.primary_600),
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Edit User", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        onSave(user.copy(username = username, email = email, password = password))
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    ) {
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewComponente3() {
    val navController = rememberNavController()
    UsuariosView(navController, User(1, "username", "julisa leon", "julisa@gmail.com", "123456"))
}
