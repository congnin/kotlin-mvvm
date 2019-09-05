package com.ptbc.kotlin_mvvm.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ptbc.kotlin_mvvm.data.model.db.Question
import io.reactivex.Single

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Question>)

    @Query("SELECT * FROM questions")
    fun loadAll(): Single<List<Question>>
}