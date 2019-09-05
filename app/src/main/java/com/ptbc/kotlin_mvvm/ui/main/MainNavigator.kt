package com.ptbc.kotlin_mvvm.ui.main

interface MainNavigator {

    fun handleError(throwable: Throwable)

    fun openLoginActivity()
}