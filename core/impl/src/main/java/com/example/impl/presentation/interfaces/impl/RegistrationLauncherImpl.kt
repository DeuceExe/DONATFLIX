package com.example.impl.presentation.interfaces.impl

import com.example.api.IRegistrationFragment
import com.example.api.IRegistrationLauncher
import com.example.impl.di.filmAppModule
import com.example.impl.di.viewModelModule
import com.example.impl.presentation.fragments.registration.RegistrationFragment
import org.koin.core.context.loadKoinModules

internal class RegistrationLauncherImpl :
    IRegistrationLauncher {

    private val businessKoin by lazy {
        listOf(
            viewModelModule,
            filmAppModule
        )
    }

    override fun launch(): IRegistrationFragment {
        loadKoinModules(businessKoin)
        return RegistrationFragment.build()
    }
}