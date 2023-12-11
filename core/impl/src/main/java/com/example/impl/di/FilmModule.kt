package com.example.impl.di

import com.example.api.IRegistrationLauncher
import com.example.impl.presentation.interfaces.impl.RegistrationLauncherImpl
import org.koin.dsl.module

val filmIdentificationModule = module {
    factory<IRegistrationLauncher> { RegistrationLauncherImpl() }
}