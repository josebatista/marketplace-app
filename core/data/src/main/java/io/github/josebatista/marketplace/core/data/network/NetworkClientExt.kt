package io.github.josebatista.marketplace.core.data.network

/**
 * Performs a GET request using the [NetworkClient] and automatically infers the expected response type.
 *
 * This convenience extension function uses a reified type parameter [T] to infer the class type,
 * so you don't need to explicitly pass the [clazz] parameter.
 *
 * @param T The expected type of the response data.
 * @param url The URL endpoint to which the GET request is made.
 * @param parameters Optional map of query parameters to be included in the request.
 * @param headers Optional map of request headers.
 * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
 *         or a [NetworkClientError] on failure.
 */
public suspend inline fun <reified T : Any> NetworkClient.get(
    url: String,
    parameters: Map<String, String>? = null,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = get(
    url = url,
    parameters = parameters,
    headers = headers,
    clazz = T::class
)

/**
 * Performs a POST request using the [NetworkClient] and automatically infers the expected response type.
 *
 * This convenience extension function uses a reified type parameter [T] to infer the class type,
 * so you don't need to explicitly pass the [clazz] parameter.
 *
 * @param T The expected type of the response data.
 * @param url The URL endpoint to which the POST request is made.
 * @param body The request body to be sent. It can be null if no body is needed.
 * @param headers Optional map of request headers.
 * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
 *         or a [NetworkClientError] on failure.
 */
public suspend inline fun <reified T : Any> NetworkClient.post(
    url: String,
    body: Any? = null,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = post(url = url, body = body, headers = headers, clazz = T::class)

/**
 * Performs a PUT request using the [NetworkClient] and automatically infers the expected response type.
 *
 * This convenience extension function uses a reified type parameter [T] to infer the class type,
 * so you don't need to explicitly pass the [clazz] parameter.
 *
 * @param T The expected type of the response data.
 * @param url The URL endpoint to which the PUT request is made.
 * @param body The request body to be sent. It can be null if no body is needed.
 * @param headers Optional map of request headers.
 * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
 *         or a [NetworkClientError] on failure.
 */
public suspend inline fun <reified T : Any> NetworkClient.put(
    url: String,
    body: Any? = null,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = put(url = url, body = body, headers = headers, clazz = T::class)

/**
 * Performs a DELETE request using the [NetworkClient] and automatically infers the expected response type.
 *
 * This convenience extension function uses a reified type parameter [T] to infer the class type,
 * so you don't need to explicitly pass the [clazz] parameter.
 *
 * @param T The expected type of the response data.
 * @param url The URL endpoint to which the DELETE request is made.
 * @param headers Optional map of request headers.
 * @return A [NetworkClientResponse] wrapping the response data of type [T] on success,
 *         or a [NetworkClientError] on failure.
 */
public suspend inline fun <reified T : Any> NetworkClient.delete(
    url: String,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = delete(url = url, headers = headers, clazz = T::class)
