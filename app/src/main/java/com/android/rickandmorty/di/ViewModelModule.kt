package com.android.rickandmorty.di

import com.android.rickandmorty.data.repository.Repository
import com.android.rickandmorty.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repo: RepositoryImpl): Repository


}