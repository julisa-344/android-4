package com.example.ep4_1.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.ep4_1.Models.User

class UserViewModel : ViewModel() {
    private val _loggedUser = MutableStateFlow<User?>(null)

    val loggedUser: StateFlow<User?> = _loggedUser
    fun setLoggedUser(user: User) {
        _loggedUser.value = user
    }
}