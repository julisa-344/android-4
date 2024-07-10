package com.example.ep4_1.Navigation

open class AppView (val route: String){
    object loginView: AppView("login")
    object registerView: AppView("register")
    object homeView: AppView("home")
    object usuariosView: AppView("usuarios")
}