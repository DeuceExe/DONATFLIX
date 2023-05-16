package com.example.donatflix.di

import com.example.donatflix.ResourcesProvider
import org.koin.core.module.Module
import org.koin.dsl.module

fun createUtilsModule(): Module = module {
    single { ResourcesProvider(get()) }
}