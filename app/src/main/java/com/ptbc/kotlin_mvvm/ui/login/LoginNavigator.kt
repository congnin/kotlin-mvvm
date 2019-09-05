package com.ptbc.kotlin_mvvm.ui.login

interface LoginNavigator {

    fun handleError(throwable: Throwable)

    fun login()

    fun openMainActivity()
}