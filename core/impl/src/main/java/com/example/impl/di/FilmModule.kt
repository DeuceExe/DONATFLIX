package com.example.impl.di

import com.example.api.IFilmFragmentLauncher
import com.example.impl.presentation.interfaces.impl.FilmFragmentLauncherImpl
import org.koin.dsl.module

val filmIdentificationModule = module {
    factory<IFilmFragmentLauncher> { FilmFragmentLauncherImpl() }
}