package com.ptbc.kotlin_mvvm.data.local.db.dao

import androidx.room.*
import com.ptbc.kotlin_mvvm.data.model.db.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun loadAll(): List<User>

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: List<Int>): List<User>
}