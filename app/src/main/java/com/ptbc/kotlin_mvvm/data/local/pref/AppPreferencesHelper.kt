package com.ptbc.kotlin_mvvm.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.di.PreferenceInfo
import com.ptbc.kotlin_mvvm.utils.AppConstants
import javax.inject.Inject

class AppPreferencesHelper @Inject
constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferencesHelper {

    private val mPrefs: SharedPreferences

    override var accessToken: String?
        get() = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null)
        set(accessToken) = mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply()

    override var currentUserEmail: String?
        get() = mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null)
        set(email) = mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply()

    override var currentUserId: Long?
        get() {
            val userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX)
            return if (userId == AppConstants.NULL_INDEX) null else userId
        }
        set(userId) {
            val id = userId ?: AppConstants.NULL_INDEX
            mPrefs.edit().putLong(PREF_KEY_CURRENT_USER_ID, id).apply()
        }

    override val currentUserLoggedInMode: Int
        get() = mPrefs.getInt(
            PREF_KEY_USER_LOGGED_IN_MODE,
            DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type
        )

    override var currentUserName: String?
        get() = mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null)
        set(userName) = mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply()

    override var currentUserProfilePicUrl: String?
        get() = mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null)
        set(profilePicUrl) =
            mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply()

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.type).apply()
    }

    companion object {

        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"

        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"

        private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"

        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"

        private const val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"

        private const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
    }
}