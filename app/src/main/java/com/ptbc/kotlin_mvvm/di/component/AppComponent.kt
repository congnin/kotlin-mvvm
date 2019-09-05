package com.ptbc.kotlin_mvvm.di.component

import android.app.Application
import com.ptbc.kotlin_mvvm.MyApp
import com.ptbc.kotlin_mvvm.di.builder.ActivityBuilder
import com.ptbc.kotlin_mvvm.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(app: MyApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}