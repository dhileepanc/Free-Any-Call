package com.freeanycall.app.di

import android.util.Log
import com.freeanycall.app.api.ApiInterface
import com.freeanycall.app.api.NormalApi
import com.freeanycall.app.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @NormalApi
    fun provideOkHttpClient(): OkHttpClient {
        // Retrofit logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            // Options: NONE, BASIC, HEADERS, BODY
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Custom interceptor (just URL + method)
        val apiLoggingInterceptor = Interceptor { chain ->
            val request = chain.request()
//            Log.d("API_URL", "➡️ ${request.method} ${request.url}")
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiLoggingInterceptor) // logs URL + method
            .addInterceptor(logging)              // logs headers + body
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @NormalApi
    fun provideRetrofit(@NormalApi okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiInterface(@NormalApi retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)
}

