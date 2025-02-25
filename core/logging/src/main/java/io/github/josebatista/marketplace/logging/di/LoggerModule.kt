package io.github.josebatista.marketplace.logging.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.josebatista.marketplace.logging.Logger
import io.github.josebatista.marketplace.logging.StdLoggerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoggerModule {

    @Binds
    @Singleton
    abstract fun bindLogger(logger: StdLoggerImpl): Logger
}
