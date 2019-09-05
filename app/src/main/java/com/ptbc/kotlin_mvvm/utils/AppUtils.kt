package com.ptbc.kotlin_mvvm.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.ptbc.kotlin_mvvm.R

object AppUtils {

    fun openPlayStoreForApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_market_link) + appPackageName
                    )
                )
            )
        } catch (e: android.content.ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        (context
                            .resources
                            .getString(R.string.app_google_play_store_link) + appPackageName)
                    )
                )
            )
        }

    }
}// This class is not publicly instantiable