package com.ptbc.kotlin_mvvm.data.local.db

import com.ptbc.kotlin_mvvm.data.model.db.Option
import com.ptbc.kotlin_mvvm.data.model.db.Question
import com.ptbc.kotlin_mvvm.data.model.db.User
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject
constructor(private val mAppDatabase: AppDatabase) : DbHelper {

    override val allQuestions: Observable<List<Question>>
        get() = mAppDatabase.questionDao().loadAll()
            .toObservable()

    override val allUsers: Observable<List<User>>
        get() = Observable.fromCallable<List<User>> { mAppDatabase.userDao().loadAll() }

    override val isOptionEmpty: Observable<Boolean>
        get() = mAppDatabase.optionDao().loadAll()
            .flatMapObservable { options -> Observable.just(options.isEmpty()) }

    override val isQuestionEmpty: Observable<Boolean>
        get() = mAppDatabase.questionDao().loadAll()
            .flatMapObservable { questions -> Observable.just(questions.isEmpty()) }

    override fun getOptionsForQuestionId(questionId: Long?): Observable<List<Option>> {
        return mAppDatabase.optionDao().loadAllByQuestionId(questionId)
            .toObservable()
    }

    override fun insertUser(user: User): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.userDao().insert(user)
            true
        }
    }

    override fun saveOption(option: Option): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.optionDao().insert(option)
            true
        }
    }

    override fun saveOptionList(optionList: List<Option>): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.optionDao().insertAll(optionList)
            true
        }
    }

    override fun saveQuestion(question: Question): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.questionDao().insert(question)
            true
        }
    }

    override fun saveQuestionList(questionList: List<Question>): Observable<Boolean> {
        return Observable.fromCallable {
            mAppDatabase.questionDao().insertAll(questionList)
            true
        }
    }
}