package com.example.clientapp.DI

import com.example.clientapp.Data.Network.ApiGoongMapService
import com.example.clientapp.Data.Network.ApiLayoutService
import com.example.clientapp.Data.Network.ApiPaymentService
import com.example.clientapp.Data.Network.ApiTripService
import com.example.clientapp.Data.Network.ApiUserService
import com.example.clientapp.Data.Repository.GoongMapRepositoryImpl
import com.example.clientapp.Data.Repository.LayoutRepositoryImpl
import com.example.clientapp.Data.Repository.PaymentRepositoryImpl
import com.example.clientapp.Data.Repository.TripRepositoryImpl
import com.example.clientapp.Data.Repository.UserRepositoryImpl
import com.example.clientapp.Domain.Repository.GoongMapRepository
import com.example.clientapp.Domain.Repository.LayoutRepository
import com.example.clientapp.Domain.Repository.PaymentRepository
import com.example.clientapp.Domain.Repository.TripRepository
import com.example.clientapp.Domain.Repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiUserService: ApiUserService): UserRepository {
        return UserRepositoryImpl(apiUserService)
    }

    @Provides
    @Singleton
    fun provideTripReposiory(apiTripService: ApiTripService): TripRepository {
        return TripRepositoryImpl(apiTripService)
    }

    @Provides
    @Singleton
    fun provideLayoutRepository(apiLayoutService: ApiLayoutService): LayoutRepository {
        return LayoutRepositoryImpl(apiLayoutService)
    }

    @Provides
    @Singleton
    fun provideGoongMapRepository(apiGoongMapService: ApiGoongMapService): GoongMapRepository {
        return GoongMapRepositoryImpl(apiGoongMapService)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(apiPaymentService: ApiPaymentService): PaymentRepository {
        return PaymentRepositoryImpl(apiPaymentService)
    }
}