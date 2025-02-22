package io.github.josebatista.marketplace.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import io.github.josebatista.marketplace.domain.usecase.SearchUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindSearchUseCase(impl: SearchUseCaseImpl): SearchUseCase
}