package io.github.josebatista.marketplace.data.network

import io.github.josebatista.marketplace.data.R
import io.github.josebatista.marketplace.domain.UiText
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    ): NetworkClientResponse<T> = safeCall {
        val response = client.get(url) {
            parameters?.forEach { (key, value) ->
                parameter(key, value)
            }
            headers?.forEach { (key, value) ->
                header(key, value)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    override suspend fun <T : Any> post(
        url: String,
        body: Any?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        val response = client.post(url) {
            headers?.forEach { (key, value) ->
                header(key, value)
            }
            if (body != null) {
                setBody(body)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    override suspend fun <T : Any> put(
        url: String,
        body: Any?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        val response = client.put(url) {
            headers?.forEach { (key, value) ->
                header(key, value)
            }
            if (body != null) {
                setBody(body)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    override suspend fun <T : Any> delete(
        url: String,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        val response = client.delete(url) {
            headers?.forEach { (key, value) ->
                header(key, value)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    private suspend fun <T> safeCall(
        block: suspend () -> NetworkClientResponse<T>
    ): NetworkClientResponse<T> = withContext(Dispatchers.IO) {
        runCatching {
            block()
        }.getOrElse {
            val httpStatusCode = if (it is ResponseException) {
                it.response.status.value
            } else {
                INVALID_HTTP_CODE
            }
            val message = it.message?.let { message ->
                UiText.DynamicText(message)
            } ?: UiText.StringResource(R.string.core_network_generic_error)
            NetworkClientResponse.NetworkClientError(
                code = httpStatusCode,
                message = message
            )
        }
    }

    private companion object {
        const val INVALID_HTTP_CODE = -1
    }
}
