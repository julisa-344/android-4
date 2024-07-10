package com.example.ep4_1.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ep4_1.Models.Perro

@Dao
interface PerroDao {
    @Query("SELECT * FROM perro")
    fun getAll(): List<Perro>

    @Query("SELECT * FROM perro WHERE uid IN (:perroIds)")
    fun loadAllByIds(perroIds: IntArray): List<Perro>

    @Query("SELECT * FROM perro WHERE nombre LIKE :nombre AND " +
            "raza LIKE :raza LIMIT 1")
    fun findByName(nombre: String, raza: String): Perro

    @Insert
    fun insertAll(vararg perros: Perro)

    @Delete
    fun delete(perro: Perro)
}