package io.github.josebatista.marketplace.core.data.network

import kotlin.reflect.KClass

/**
 * Represents a client capable of performing network operations.
 *
 * This interface defines methods for executing HTTP requests (GET, POST, PUT, DELETE)
 * and returning their responses wrapped in a [NetworkClientResponse]. The expected response
 * type is defined by the reified type parameter [T].
 */
public interface NetworkClient {

    /**
     * Executes a GET request to the specified [url].
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint to which the GET request is sent.
     * @param parameters Optional map of query parameters to include in the request.
     * @param headers Optional map of request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
     *         or a [NetworkClientError] on failure.
     */
    public suspend fun <T : Any> get(
        url: String,
        parameters: Map<String, String>? = null,
        headers: Map<String, String>? = null,
        clazz: KClass<T>
    ): NetworkClientResponse<T>

    /**
     * Executes a POST request to the specified [url].
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint to which the POST request is sent.
     * @param body The request body to be sent. This can be null if no body is required.
     * @param headers Optional map of request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
     *         or a [NetworkClientError] on failure.
     */
    public suspend fun <T : Any> post(
        url: String,
        body: Any? = null,
        headers: Map<String, String>? = null,
        clazz: KClass<T>,
    ): NetworkClientResponse<T>

    /**
     * Executes a PUT request to the specified [url].
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint to which the PUT request is sent.
     * @param body The request body to be sent. This can be null if no body is required.
     * @param headers Optional map of request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
     *         or a [NetworkClientError] on failure.
     */
    public suspend fun <T : Any> put(
        url: String,
        body: Any? = null,
        headers: Map<String, String>? = null,
        clazz: KClass<T>,
    ): NetworkClientResponse<T>

    /**
     * Executes a DELETE request to the specified [url].
     *
     * @param T The expected type of the response data.
     * @param url The URL endpoint to which the DELETE request is sent.
     * @param headers Optional map of request headers.
     * @param clazz The [KClass] of the expected response data.
     * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
     *         or a [NetworkClientError] on failure.
     */
    public suspend fun <T : Any> delete(
        url: String,
        headers: Map<String, String>? = null,
        clazz: KClass<T>,
    ): NetworkClientResponse<T>
}
