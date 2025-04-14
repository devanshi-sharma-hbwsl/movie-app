// AppModule.kt
package com.example.movieapp.di

import com.example.movieapp.featureA.data.network.repository.FetchMovieRepoImpl
import com.example.movieapp.featureA.domain.repository.FetchMovieRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindFetchMovieRepo(
        fetchMovieRepoImpl: FetchMovieRepoImpl
    ): FetchMovieRepo
}
