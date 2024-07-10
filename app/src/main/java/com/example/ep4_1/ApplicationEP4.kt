package com.example.ep4_1

import androidx.room.Room
import com.example.ep4_1.database.AppDatabase

import android.app.Application

class ApplicationEP4 : Application(){
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }
}