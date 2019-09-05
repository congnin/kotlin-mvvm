package com.ptbc.kotlin_mvvm.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.reflect.TypeToken
import com.ptbc.kotlin_mvvm.data.local.db.DbHelper
import com.ptbc.kotlin_mvvm.data.local.pref.PreferencesHelper
import com.ptbc.kotlin_mvvm.data.model.api.*
import com.ptbc.kotlin_mvvm.data.model.db.Option
import com.ptbc.kotlin_mvvm.data.model.db.Question
import com.ptbc.kotlin_mvvm.data.model.db.User
import com.ptbc.kotlin_mvvm.data.model.other.QuestionCardData
import com.ptbc.kotlin_mvvm.data.remote.ApiHeader
import com.ptbc.kotlin_mvvm.data.remote.ApiHelper
import com.ptbc.kotlin_mvvm.utils.AppConstants
import com.ptbc.kotlin_mvvm.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject
constructor(
    private val mContext: Context,
    private val mDbHelper: DbHelper,
    private val mPreferencesHelper: PreferencesHelper,
    private val mApiHelper: ApiHelper,
    private val mGson: Gson
) : DataManager {

    override var accessToken: String?
        get() = mPreferencesHelper.accessToken
        set(accessToken) {
            mPreferencesHelper.accessToken = accessToken
            mApiHelper.apiHeader.protectedApiHeader.accessToken = accessToken
        }

    override val allQuestions: Observable<List<Question>>
        get() = mDbHelper.allQuestions

    override val allUsers: Observable<List<User>>
        get() = mDbHelper.allUsers

    override val apiHeader: ApiHeader
        get() = mApiHelper.apiHeader

    override val blogApiCall: Single<BlogResponse>
        get() = mApiHelper.blogApiCall

    override var currentUserEmail: String?
        get() = mPreferencesHelper.currentUserEmail
        set(email) {
            mPreferencesHelper.currentUserEmail = email
        }

    override var currentUserId: Long?
        get() = mPreferencesHelper.currentUserId
        set(userId) {
            mPreferencesHelper.currentUserId = userId
        }

    override val currentUserLoggedInMode: Int
        get() = mPreferencesHelper.currentUserLoggedInMode

    override var currentUserName: String?
        get() = mPreferencesHelper.currentUserName
        set(userName) {
            mPreferencesHelper.currentUserName = userName
        }

    override var currentUserProfilePicUrl: String?
        get() = mPreferencesHelper.currentUserProfilePicUrl
        set(profilePicUrl) {
            mPreferencesHelper.currentUserProfilePicUrl = profilePicUrl
        }

    override val openSourceApiCall: Single<OpenSourceResponse>
        get() = mApiHelper.openSourceApiCall

    override val questionCardData: Observable<List<QuestionCardData>>
        get() = mDbHelper.allQuestions
            .flatMap { questions -> Observable.fromIterable(questions) }
            .flatMap { question ->
                Observable.zip(
                    mDbHelper.getOptionsForQuestionId(question.id),
                    Observable.just(question),
                    BiFunction<List<Option>, Question, QuestionCardData> { options, question1 ->
                        QuestionCardData(
                            question1,
                            options
                        )
                    })
            }
            .toList()
            .toObservable()

    override val isOptionEmpty: Observable<Boolean>
        get() = mDbHelper.isOptionEmpty

    override val isQuestionEmpty: Observable<Boolean>
        get() = mDbHelper.isQuestionEmpty

    override fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): Single<LoginResponse> {
        return mApiHelper.doFacebookLoginApiCall(request)
    }

    override fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): Single<LoginResponse> {
        return mApiHelper.doGoogleLoginApiCall(request)
    }

    override fun doLogoutApiCall(): Single<LogoutResponse> {
        return mApiHelper.doLogoutApiCall()
    }

    override fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): Single<LoginResponse> {
        return mApiHelper.doServerLoginApiCall(request)
    }

    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getOptionsForQuestionId(questionId: Long?): Observable<List<Option>> {
        return mDbHelper.getOptionsForQuestionId(questionId)
    }

    override fun insertUser(user: User): Observable<Boolean> {
        return mDbHelper.insertUser(user)
    }

    override fun saveOption(option: Option): Observable<Boolean> {
        return mDbHelper.saveOption(option)
    }

    override fun saveOptionList(optionList: List<Option>): Observable<Boolean> {
        return mDbHelper.saveOptionList(optionList)
    }

    override fun saveQuestion(question: Question): Observable<Boolean> {
        return mDbHelper.saveQuestion(question)
    }

    override fun saveQuestionList(questionList: List<Question>): Observable<Boolean> {
        return mDbHelper.saveQuestionList(questionList)
    }

    override fun seedDatabaseOptions(): Observable<Boolean> {
        return mDbHelper.isOptionEmpty
            .concatMap { isEmpty ->
                if (isEmpty) {
                    val type = object : TypeToken<List<Option>>() {}.type
                    val optionList: List<Option> = mGson.fromJson(
                        CommonUtils.loadJSONFromAsset(
                            mContext,
                            AppConstants.SEED_DATABASE_OPTIONS
                        ), type
                    )
                    return@concatMap saveOptionList(optionList)
                } else {
                    Observable.just(false)
                }
            }
    }

    override fun seedDatabaseQuestions(): Observable<Boolean> {
        return mDbHelper.isQuestionEmpty
            .concatMap { isEmpty ->
                if (isEmpty) {
                    val type = `$Gson$Types`.newParameterizedTypeWithOwner(
                        null, List::class.java, Question::class.java
                    )
                    val questionList = mGson.fromJson(
                        CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS),
                        type
                    ) as List<Question>
                    return@concatMap saveQuestionList(questionList)
                }
                Observable.just(false)
            }
    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(
            null, null,
            DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT, null, null, null
        )
    }

    override fun updateApiHeader(userId: Long?, token: String?) {
        mApiHelper.apiHeader.protectedApiHeader.userId = userId
        mApiHelper.apiHeader.protectedApiHeader.accessToken = accessToken
    }

    override fun updateUserInfo(
        token: String?,
        userId: Long?,
        loggedInMode: DataManager.LoggedInMode,
        userName: String?,
        email: String?,
        profilePicPath: String?
    ) {

        accessToken = token
        currentUserId = userId
        setCurrentUserLoggedInMode(loggedInMode)
        currentUserName = userName
        currentUserEmail = email
        currentUserProfilePicUrl = profilePicPath

        updateApiHeader(userId, accessToken)
    }

    companion object {

        private const val TAG = "AppDataManager"
    }
}