package io.github.josebatista.marketplace.data.network

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

public suspend inline fun <reified T : Any> NetworkClient.post(
    url: String,
    body: Any? = null,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = post(url = url, body = body, headers = headers, clazz = T::class)

public suspend inline fun <reified T : Any> NetworkClient.put(
    url: String,
    body: Any? = null,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = put(url = url, body = body, headers = headers, clazz = T::class)

public suspend inline fun <reified T : Any> NetworkClient.delete(
    url: String,
    headers: Map<String, String>? = null,
): NetworkClientResponse<T> = delete(url = url, headers = headers, clazz = T::class)
