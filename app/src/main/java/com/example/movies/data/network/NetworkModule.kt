package com.example.movies.data.network

import android.content.Context
import com.example.movies.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiServices(@ApplicationContext context: Context): Api {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }


    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headersInterceptor(context))
            .addInterceptor(provideLoggingInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun headersInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request: Request
            val builder = original.newBuilder()
            builder.header(
                "Authorization",
                "Bearer ${context.getString(R.string.api_authurization)}"
            )
            builder.method(original.method, original.body)
            request = builder.build()
            chain.proceed(request)
        }
    }

    private fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

}