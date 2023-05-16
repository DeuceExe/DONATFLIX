package com.example.donatflix

import android.app.Application
import com.example.donatflix.di.createRetrofitModule
import com.example.donatflix.di.createUtilsModule
import com.example.impl.di.filmIdentificationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module

class BaseApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        insertKoin(this, initModules())
    }

    private fun initModules(): List<Module> = listOf(
        createRetrofitModule(),
        createUtilsModule(),
        filmIdentificationModule
    )

    private fun insertKoin(application: Application, moduleList: List<Module>) {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(moduleList)
        }
    }

    companion object {

        const val API_KEY_TAG = "X-API-KEY"
        const val BASE_FILM_URL = "https://api.kinopoisk.dev/"
    }
}