package com.ptbc.kotlin_mvvm.data.local.db

import com.ptbc.kotlin_mvvm.data.model.db.Option
import com.ptbc.kotlin_mvvm.data.model.db.Question
import com.ptbc.kotlin_mvvm.data.model.db.User
import io.reactivex.Observable

interface DbHelper {

    val allQuestions: Observable<List<Question>>

    val allUsers: Observable<List<User>>

    val isOptionEmpty: Observable<Boolean>

    val isQuestionEmpty: Observable<Boolean>

    fun getOptionsForQuestionId(questionId: Long?): Observable<List<Option>>

    fun insertUser(user: User): Observable<Boolean>

    fun saveOption(option: Option): Observable<Boolean>

    fun saveOptionList(optionList: List<Option>): Observable<Boolean>

    fun saveQuestion(question: Question): Observable<Boolean>

    fun saveQuestionList(questionList: List<Question>): Observable<Boolean>
}