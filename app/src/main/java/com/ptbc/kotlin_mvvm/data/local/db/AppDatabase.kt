package com.ptbc.kotlin_mvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ptbc.kotlin_mvvm.data.local.db.dao.OptionDao
import com.ptbc.kotlin_mvvm.data.local.db.dao.QuestionDao
import com.ptbc.kotlin_mvvm.data.local.db.dao.UserDao
import com.ptbc.kotlin_mvvm.data.model.db.Option
import com.ptbc.kotlin_mvvm.data.model.db.Question
import com.ptbc.kotlin_mvvm.data.model.db.User

@Database(entities = [User::class, Question::class, Option::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun optionDao(): OptionDao

    abstract fun questionDao(): QuestionDao

    abstract fun userDao(): UserDao
}