package io.github.josebatista.marketplace.data.network

import kotlin.reflect.KClass

public interface NetworkClient {
    public suspend fun <T : Any> get(
        url: String,
        parameters: Map<String, String>? = null,
        headers: Map<String, String>? = null,
        clazz: KClass<T>
    ): NetworkClientResponse<T>

    public suspend fun <T : Any> post(
        url: String, body: Any? = null,
        headers: Map<String, String>? = null,
        clazz: KClass<T>,
    ): NetworkClientResponse<T>

    public suspend fun <T : Any> put(
        url: String,
        body: Any? = null,
        headers: Map<String, String>? = null,
        clazz: KClass<T>,
    ): NetworkClientResponse<T>

    public suspend fun <T : Any> delete(
        url: String,
        headers: Map<String, String>? = null,
        clazz: KClass<T>,
    ): NetworkClientResponse<T>
}
