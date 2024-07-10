package com.example.ep4_1.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Perro(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "nombre") val nombre: String?,
)