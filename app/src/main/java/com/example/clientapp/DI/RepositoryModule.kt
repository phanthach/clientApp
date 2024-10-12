package com.example.clientapp.DI

import com.example.clientapp.Data.Network.ApiUserService
import com.example.clientapp.Data.Repository.UserRepositoryImpl
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
}