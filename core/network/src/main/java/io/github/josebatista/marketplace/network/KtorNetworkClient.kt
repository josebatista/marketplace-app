package io.github.josebatista.marketplace.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.util.reflect.TypeInfo
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
internal class KtorNetworkClient @Inject constructor(
    private val client: HttpClient
) : NetworkClient {

    override suspend fun <T : Any> get(
        url: String,
        parameters: Map<String, String>?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): T {
        return client.get(url) {
            parameters?.forEach { (key, value) ->
                parameter(key, value)
            }
            headers?.forEach { (key, value) ->
                header(key, value)
            }
        }.body(TypeInfo(type = clazz))
    }

    override suspend fun <T : Any> post(
        url: String,
        body: Any?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): T {
        return client.post(url) {
            headers?.forEach { (key, value) ->
                header(key, value)
            }
            if (body != null) {
                setBody(body)
            }
        }.body(TypeInfo(type = clazz))
    }

    override suspend fun <T : Any> put(
        url: String,
        body: Any?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): T {
        return client.put(url) {
            headers?.forEach { (key, value) ->
                header(key, value)
            }
            if (body != null) {
                setBody(body)
            }
        }.body(TypeInfo(type = clazz))
    }

    override suspend fun <T : Any> delete(
        url: String,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): T {
        return client.delete(url) {
            headers?.forEach { (key, value) ->
                header(key, value)
            }
        }.body(TypeInfo(type = clazz))
    }
}
