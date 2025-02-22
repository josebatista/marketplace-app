package io.github.josebatista.marketplace.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.josebatista.marketplace.data.repository.SearchRepositoryImpl
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

}
