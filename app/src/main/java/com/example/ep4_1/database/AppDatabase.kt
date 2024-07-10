package com.example.ep4_1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ep4_1.DAOs.UserDao
import com.example.ep4_1.Models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}