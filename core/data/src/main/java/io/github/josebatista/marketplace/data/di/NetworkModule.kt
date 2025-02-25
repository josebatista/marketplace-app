package io.github.josebatista.marketplace.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = false
            })
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }

    @Provides
    @Singleton
    fun bindNetworkClient(httpClient: HttpClient): io.github.josebatista.marketplace.data.network.NetworkClient =
        io.github.josebatista.marketplace.data.network.KtorNetworkClient(httpClient)
}
