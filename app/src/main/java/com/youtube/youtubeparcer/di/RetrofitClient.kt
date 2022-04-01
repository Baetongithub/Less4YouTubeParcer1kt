package com.youtube.youtubeparcer.di

import com.youtube.youtubeparcer.BuildConfig.BASE_URL
import com.youtube.youtubeparcer.data.network.YouTubeAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideYouTubeAPI(get()) }
    factory { provideOkHttClient() }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

}

fun provideOkHttClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

fun provideYouTubeAPI(retrofit: Retrofit): YouTubeAPI = retrofit.create(YouTubeAPI::class.java)