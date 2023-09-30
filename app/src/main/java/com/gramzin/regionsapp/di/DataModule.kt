package com.gramzin.regionsapp.di

import com.gramzin.regionsapp.data.network.RegionsApiService
import com.gramzin.regionsapp.data.repository.RegionsRepositoryImpl
import com.gramzin.regionsapp.domain.repository.RegionsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindRegionsRepositoryToImpl(
        regionsRepository: RegionsRepositoryImpl
    ): RegionsRepository

    companion object{

        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        @Singleton
        @Provides
        fun provideRegionsApiService(client: OkHttpClient): Retrofit {

            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://vmeste.wildberries.ru/")
                .client(client)
                .build()
        }

        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): RegionsApiService {
            return retrofit.create(RegionsApiService::class.java)
        }

    }
}