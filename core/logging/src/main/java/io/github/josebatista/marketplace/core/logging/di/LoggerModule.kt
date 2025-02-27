package io.github.josebatista.marketplace.core.logging.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.josebatista.marketplace.core.logging.Logger
import io.github.josebatista.marketplace.core.logging.StdLoggerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoggerModule {

    @Binds
    @Singleton
    abstract fun bindLogger(logger: StdLoggerImpl): Logger
}
