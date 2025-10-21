package com.example.s8081735assignment2.di

import com.example.s8081735assignment2.data.api.NitApiService
import com.example.s8081735assignment2.data.repository.NitRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

// Dagger Hilt module for dependency injection.
// Provides singleton instances of Moshi, OkHttpClient, NitApiService, and NitRepository.

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://nit3213api.onrender.com/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideApiService(moshi: Moshi, client: OkHttpClient): NitApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(NitApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: NitApiService): NitRepository = NitRepository(apiService)
}
