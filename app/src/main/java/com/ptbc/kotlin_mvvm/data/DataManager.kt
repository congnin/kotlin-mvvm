package com.ptbc.kotlin_mvvm.data

import com.ptbc.kotlin_mvvm.data.local.db.DbHelper
import com.ptbc.kotlin_mvvm.data.local.pref.PreferencesHelper
import com.ptbc.kotlin_mvvm.data.model.other.QuestionCardData
import com.ptbc.kotlin_mvvm.data.remote.ApiHelper
import io.reactivex.Observable

interface DataManager : DbHelper, PreferencesHelper, ApiHelper {

    val questionCardData: Observable<List<QuestionCardData>>

    fun seedDatabaseOptions(): Observable<Boolean>

    fun seedDatabaseQuestions(): Observable<Boolean>

    fun setUserAsLoggedOut()

    fun updateApiHeader(userId: Long?, token: String?)

    fun updateUserInfo(
        token: String?,
        userId: Long?,
        loggedInMode: LoggedInMode,
        userName: String?,
        email: String?,
        profilePicPath: String?
    )

    enum class LoggedInMode(val type: Int) {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3)
    }
}