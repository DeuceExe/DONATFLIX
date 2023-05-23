package com.example.impl.presentation.interfaces.impl

import com.example.api.IFilmFragment
import com.example.api.IFilmFragmentLauncher
import com.example.impl.di.filmAppModule
import com.example.impl.di.viewModelModule
import com.example.impl.presentation.fragments.film.FilmsFragment
import com.example.impl.presentation.registration.RegistrationFragment
import org.koin.core.context.loadKoinModules

internal class FilmFragmentLauncherImpl :
    IFilmFragmentLauncher {

    private val businessKoin by lazy {
        listOf(
            viewModelModule,
            filmAppModule
        )
    }

    override fun launch(): IFilmFragment {
        loadKoinModules(businessKoin)
        return RegistrationFragment.build()
    }
}