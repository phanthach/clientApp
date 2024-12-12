package com.example.clientapp.DI

import com.example.clientapp.Data.Network.ApiGoongMapService
import com.example.clientapp.Data.Network.ApiLayoutService
import com.example.clientapp.Data.Network.ApiTripService
import com.example.clientapp.Data.Network.ApiUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "http://10.0.0.139:8080/"
    private const val GOONGMAP_URL = "https://rsapi.goong.io/"

    @Provides
    @Singleton
    @Named("RetrofitClient")
    fun RetrofitClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    @Provides
    @Singleton
    @Named("RetrofitGoongMap")
    fun RetrofitGoongMap(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GOONGMAP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideGoongMapService(@Named("RetrofitGoongMap") retrofit: Retrofit): ApiGoongMapService {
        return retrofit.create(ApiGoongMapService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiUserService(@Named("RetrofitClient") retrofit: Retrofit): ApiUserService{
        return retrofit.create(ApiUserService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiTripService(@Named("RetrofitClient") retrofit: Retrofit): ApiTripService {
        return retrofit.create(ApiTripService::class.java)
    }

    @Provides
    @Singleton
    fun privideApiLayoutService(@Named("RetrofitClient") retrofit: Retrofit): ApiLayoutService {
        return retrofit.create(ApiLayoutService::class.java)
    }
}