package com.example.adidata.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
//    single { provideHttpClient() }
//    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
}

const val connectTimeout: Long = 30
const val readTimeout: Long = 30

//fun provideHttpClient(): OkHttpClient {
//    val okHttpClientBuilder = OkHttpClient.Builder()
//        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
//        .readTimeout(readTimeout, TimeUnit.SECONDS)
//    if (BuildConfig.DEBUG) {
//        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.HEADERS
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
//    }
//    okHttpClientBuilder.addInterceptor(NetworkInterceptor())
//    okHttpClientBuilder.build()
//    return okHttpClientBuilder.build()
//}

fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}