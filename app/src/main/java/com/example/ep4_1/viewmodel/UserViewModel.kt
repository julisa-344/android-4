package com.example.ep4_1.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.ep4_1.Models.User

class UserViewModel : ViewModel() {
    // MutableStateFlow es privado para evitar modificaciones externas
    private val _loggedUser = MutableStateFlow<User?>(null)

    // StateFlow es público para observar los cambios
    val loggedUser: StateFlow<User?> = _loggedUser

    fun setLoggedUser(user: User) {
        _loggedUser.value = user
    }
}