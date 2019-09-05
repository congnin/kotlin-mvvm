package com.ptbc.kotlin_mvvm.data.local.pref

import com.ptbc.kotlin_mvvm.data.DataManager

interface PreferencesHelper {

    var accessToken: String?

    var currentUserEmail: String?

    var currentUserId: Long?

    val currentUserLoggedInMode: Int

    var currentUserName: String?

    var currentUserProfilePicUrl: String?

    fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode)
}