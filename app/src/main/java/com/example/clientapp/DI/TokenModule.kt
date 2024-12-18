package com.example.clientapp.DI

import android.content.Context
import android.content.SharedPreferences
import com.example.clientapp.Data.Repository.TokenRepositoryImpl
import com.example.clientapp.Domain.Repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Singleton
    fun provideTokenRepository(sharedPreferences: SharedPreferences): TokenRepository {
        return TokenRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("tokenAuthen", Context.MODE_PRIVATE)
    }
}