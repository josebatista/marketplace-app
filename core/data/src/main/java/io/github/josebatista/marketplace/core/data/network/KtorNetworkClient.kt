package io.github.josebatista.marketplace.core.data.network

import io.github.josebatista.marketplace.core.data.R
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.core.logging.Logger
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

/**
 * A [NetworkClient] implementation that uses Ktor's [HttpClient] to perform network operations.
 *
 * This class performs HTTP requests (GET, POST, PUT, DELETE) using the Ktor client, logs request details using
 * the provided [Logger], and wraps responses in a [NetworkClientResponse]. It uses a common [safeCall] mechanism to
 * handle exceptions and transform them into [NetworkClientResponse.NetworkClientError] instances.
 *
 * @property client The [HttpClient] instance used for making network requests.
 * @property logger The [Logger] used to log request details and errors.
 */
@Singleton
internal class KtorNetworkClient @Inject constructor(
    private val client: HttpClient,
    private val logger: Logger
) : NetworkClient {

    /**
     * Executes an HTTP GET request.
     *
     * The GET request is made to the specified [url] with optional [parameters] and [headers].
     * For each parameter and header, a log message is generated.
     * The response body is parsed into an instance of type [T] using the provided [clazz] information.
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint for the GET request.
     * @param parameters Optional query parameters.
     * @param headers Optional request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] containing the parsed data on success, or an error on failure.
     */
    override suspend fun <T : Any> get(
        url: String,
        parameters: Map<String, String>?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        logger.sendLog("GET request to $url")
        val response = client.get(url) {
            parameters?.forEach { (key, value) ->
                logger.sendLog("Parameter [$key] = $value")
                parameter(key, value)
            }
            headers?.forEach { (key, value) ->
                logger.sendLog("Header [$key] = $value")
                header(key, value)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    /**
     * Executes an HTTP POST request.
     *
     * The POST request is made to the specified [url] with an optional [body] and optional [headers].
     * If a [body] is provided, it is set in the request and logged. The response body is parsed into an
     * instance of type [T] using the provided [clazz] information.
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint for the POST request.
     * @param body The request body to be sent; can be null.
     * @param headers Optional request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] containing the parsed data on success, or an error on failure.
     */
    override suspend fun <T : Any> post(
        url: String,
        body: Any?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        logger.sendLog("POST request to $url")
        val response = client.post(url) {
            headers?.forEach { (key, value) ->
                logger.sendLog("Header [$key] = $value")
                header(key, value)
            }
            if (body != null) {
                logger.sendLog("Body = $body")
                setBody(body)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    /**
     * Executes an HTTP PUT request.
     *
     * The PUT request is made to the specified [url] with an optional [body] and optional [headers].
     * If a [body] is provided, it is set in the request and logged. The response body is parsed into an
     * instance of type [T] using the provided [clazz] information.
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint for the PUT request.
     * @param body The request body to be sent; can be null.
     * @param headers Optional request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] containing the parsed data on success, or an error on failure.
     */
    override suspend fun <T : Any> put(
        url: String,
        body: Any?,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        logger.sendLog("PUT request to $url")
        val response = client.put(url) {
            headers?.forEach { (key, value) ->
                logger.sendLog("Header [$key] = $value")
                header(key, value)
            }
            if (body != null) {
                logger.sendLog("Body = $body")
                setBody(body)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    /**
     * Executes an HTTP DELETE request.
     *
     * The DELETE request is made to the specified [url] with optional [headers].
     * The response body is parsed into an instance of type [T] using the provided [clazz] information.
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint for the DELETE request.
     * @param headers Optional request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] containing the parsed data on success, or an error on failure.
     */
    override suspend fun <T : Any> delete(
        url: String,
        headers: Map<String, String>?,
        clazz: KClass<T>,
    ): NetworkClientResponse<T> = safeCall {
        logger.sendLog("DELETE request to $url")
        val response = client.delete(url) {
            headers?.forEach { (key, value) ->
                logger.sendLog("Header [$key] = $value")
                header(key, value)
            }
        }
        NetworkClientResponse.NetworkClientResponseSuccess(
            code = response.status.value,
            data = response.body(TypeInfo(type = clazz))
        )
    }

    /**
     * Executes a safe network call by wrapping the provided [block] in a try-catch block.
     *
     * This function runs the [block] in the IO dispatcher and catches any exceptions that may occur.
     * In case of an exception, it logs the error, determines an appropriate HTTP status code, and returns a
     * [NetworkClientResponse.NetworkClientError] with a dynamic error message.
     *
     * @param T The expected type of the response data.
     * @param block A suspend lambda that executes the network request and returns
     * a [NetworkClientResponse] of type [T].
     * @return A [NetworkClientResponse] containing either the successful result or an error.
     */
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
            logger.sendLog("Error: ${it.localizedMessage}")
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
