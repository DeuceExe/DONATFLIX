package com.example.impl.di

import com.example.impl.presentation.fragments.film.FilmViewModel
import com.example.impl.presentation.fragments.film.FilmsFragment
import com.example.impl.presentation.fragments.filmDetails.FilmDetailFragment
import com.example.impl.presentation.fragments.filmDetails.FilmDetailViewModel
import com.example.impl.presentation.fragments.registration.RegistrationFragment
import com.example.impl.rest.IFilmsApi
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal val viewModelModule = module {
    single { get<Retrofit>().create(IFilmsApi::class.java) }
    viewModel { FilmViewModel(get()) }
    viewModel { FilmDetailViewModel(get()) }
}

internal val filmAppModule = module {
    fragment { FilmsFragment() }
    fragment { FilmDetailFragment() }
    fragment { RegistrationFragment() }
}
