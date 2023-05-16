package com.example.donatflix.di

import com.example.donatflix.BaseApplication.Companion.API_KEY_TAG
import com.example.donatflix.BaseApplication.Companion.BASE_FILM_URL
import com.example.donatflix.R
import com.example.donatflix.ResourcesProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createRetrofitModule(): Module = module {
    single { initRetrofit(createOkHttp(get())) }
}

private fun createOkHttp(resources: ResourcesProvider): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            return@Interceptor chain.proceed(
                chain.request()
                    .newBuilder()
                    .header(API_KEY_TAG, resources.getString(R.string.api_key))
                    .build()
            )
        })
        .addInterceptor(interceptor)
        .build()
}

private fun initRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BASE_FILM_URL)
    .client(okHttpClient)
    .callFactory(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create()).build()