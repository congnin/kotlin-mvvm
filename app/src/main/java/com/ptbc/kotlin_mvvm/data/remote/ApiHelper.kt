package com.ptbc.kotlin_mvvm.data.remote

import com.ptbc.kotlin_mvvm.data.model.api.*
import io.reactivex.Single

interface ApiHelper {

    val apiHeader: ApiHeader

    val blogApiCall: Single<BlogResponse>

    val openSourceApiCall: Single<OpenSourceResponse>

    fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): Single<LoginResponse>

    fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): Single<LoginResponse>

    fun doLogoutApiCall(): Single<LogoutResponse>

    fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): Single<LoginResponse>
}