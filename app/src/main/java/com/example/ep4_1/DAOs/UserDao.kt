package com.example.ep4_1.DAOs
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ep4_1.Models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE fullname LIKE :fullName  LIMIT 1")
    fun findByName(fullName: String): User

    @Insert
    fun insertAll(vararg users: User)


    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    fun insert(newUser: User) {
        insertAll(newUser)
    }

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun findUser(email: String, password: String): User?
}