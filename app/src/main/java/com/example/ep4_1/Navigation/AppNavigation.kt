package com.example.ep4_1.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ep4_1.Views.HomeView
import com.example.ep4_1.Views.LoginView
import com.example.ep4_1.Views.RegisterView
import com.example.ep4_1.Views.UsuariosView
import com.example.ep4_1.viewmodel.UserViewModel

@Composable
fun appNavigation(){
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val loggedUser = userViewModel.loggedUser.collectAsState().value

    NavHost(navController = navController, startDestination = AppView.loginView.route) {
        composable(AppView.loginView.route){LoginView(navController)}
        composable(AppView.registerView.route){RegisterView(navController)}
        composable(AppView.homeView.route){HomeView(navController, loggedUser!!)}
    }
}