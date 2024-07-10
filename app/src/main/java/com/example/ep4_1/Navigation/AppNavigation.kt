package com.example.ep4_1.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ep4_1.Models.User
import com.example.ep4_1.Views.HomeView
import com.example.ep4_1.Views.LoginView
import com.example.ep4_1.Views.RegisterView
import com.example.ep4_1.Views.UsuariosView
import com.example.ep4_1.viewmodel.UserViewModel

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val loggedUser = userViewModel.loggedUser.collectAsState().value

    NavHost(navController = navController, startDestination = if (loggedUser != null) AppView.homeView.route else AppView.loginView.route) {
        composable(AppView.loginView.route) { LoginView(navController) }
        composable("register") { RegisterView(navController) }
        composable(
            route = "home/{username}/{email}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            val email = backStackEntry.arguments?.getString("email")
            HomeView(username = username, email = email, navController = navController)
        }
        composable(AppView.usuariosView.route) {
            UsuariosView(navController, User(1, "username", "julisa leon", "julisa@gmail.com", "123456"))
        }
    }
}
