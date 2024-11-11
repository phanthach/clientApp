package com.example.clientapp.DI

import com.example.clientapp.Data.Network.ApiLayoutService
import com.example.clientapp.Data.Network.ApiTripService
import com.example.clientapp.Data.Network.ApiUserService
import com.example.clientapp.Data.Network.AuthInterceptor
import com.example.clientapp.Domain.Repository.TokenRepository
import com.google.firebase.database.core.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "http://10.0.0.141:8080/"

    @Provides
    @Singleton
    fun RetrofitClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    @Provides
    @Singleton
    fun provideApiUserService(retrofit: Retrofit): ApiUserService{
        return retrofit.create(ApiUserService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiTripService(retrofit: Retrofit): ApiTripService {
        return retrofit.create(ApiTripService::class.java)
    }

    @Provides
    @Singleton
    fun privideApiLayoutService(retrofit: Retrofit): ApiLayoutService {
        return retrofit.create(ApiLayoutService::class.java)
    }
}