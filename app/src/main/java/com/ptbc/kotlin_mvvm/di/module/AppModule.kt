package com.ptbc.kotlin_mvvm.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ptbc.kotlin_mvvm.BuildConfig
import com.ptbc.kotlin_mvvm.R
import com.ptbc.kotlin_mvvm.data.AppDataManager
import com.ptbc.kotlin_mvvm.data.DataManager
import com.ptbc.kotlin_mvvm.data.local.db.AppDatabase
import com.ptbc.kotlin_mvvm.data.local.db.AppDbHelper
import com.ptbc.kotlin_mvvm.data.local.db.DbHelper
import com.ptbc.kotlin_mvvm.data.local.pref.AppPreferencesHelper
import com.ptbc.kotlin_mvvm.data.local.pref.PreferencesHelper
import com.ptbc.kotlin_mvvm.data.remote.ApiHeader
import com.ptbc.kotlin_mvvm.data.remote.ApiHelper
import com.ptbc.kotlin_mvvm.data.remote.AppApiHelper
import com.ptbc.kotlin_mvvm.di.ApiInfo
import com.ptbc.kotlin_mvvm.di.DatabaseInfo
import com.ptbc.kotlin_mvvm.di.PreferenceInfo
import com.ptbc.kotlin_mvvm.utils.AppConstants
import com.ptbc.kotlin_mvvm.utils.rx.AppSchedulerProvider
import com.ptbc.kotlin_mvvm.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @ApiInfo
    internal fun provideApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @DatabaseInfo
    internal fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    internal fun provideProtectedApiHeader(
        @ApiInfo apiKey: String,
        preferencesHelper: PreferencesHelper
    ): ApiHeader.ProtectedApiHeader {
        return ApiHeader.ProtectedApiHeader(
            apiKey,
            preferencesHelper.currentUserId,
            preferencesHelper.accessToken
        )
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}