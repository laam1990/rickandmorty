package com.android.rickandmorty.di

import com.android.rickandmorty.data.network.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InjectionModule {

    @Singleton
    @Provides
    fun provideApolloClient() = ApolloClient()

}