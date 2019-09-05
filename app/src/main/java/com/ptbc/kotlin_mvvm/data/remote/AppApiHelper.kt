package com.ptbc.kotlin_mvvm.data.remote

import com.ptbc.kotlin_mvvm.data.model.api.*
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject
constructor(override val apiHeader: ApiHeader) : ApiHelper {

    override val blogApiCall: Single<BlogResponse>
        get() = Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
            .addHeaders(apiHeader.protectedApiHeader)
            .build()
            .getObjectSingle(BlogResponse::class.java)

    override val openSourceApiCall: Single<OpenSourceResponse>
        get() = Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
            .addHeaders(apiHeader.protectedApiHeader)
            .build()
            .getObjectSingle(OpenSourceResponse::class.java)

    override fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): Single<LoginResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
            .addHeaders(apiHeader.publicApiHeader)
            .addBodyParameter(request)
            .build()
            .getObjectSingle(LoginResponse::class.java)
    }

    override fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): Single<LoginResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
            .addHeaders(apiHeader.publicApiHeader)
            .addBodyParameter(request)
            .build()
            .getObjectSingle(LoginResponse::class.java)
    }

    override fun doLogoutApiCall(): Single<LogoutResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
            .addHeaders(apiHeader.protectedApiHeader)
            .build()
            .getObjectSingle(LogoutResponse::class.java)
    }

    override fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): Single<LoginResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
            .addHeaders(apiHeader.publicApiHeader)
            .addBodyParameter(request)
            .build()
            .getObjectSingle(LoginResponse::class.java)
    }
}