package io.github.josebatista.marketplace.data.network

import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.logging.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

@Serializable
internal data class DummyResponse(val content: String)

internal class DummyLogger : Logger {
    val logs = mutableListOf<String>()
    override fun sendLog(message: String) {
        logs.add(message)
    }
}

internal class KtorNetworkClientOkHttpTest {

    private val parameters = mapOf("q" to "testQuery", "offset" to "10")
    private val headers = mapOf("Authorization" to "Bearer token")

    private fun createHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun `get returns NetworkClientResponseSuccess on valid response`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            val jsonResponse = """{"content": "success"}"""
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(jsonResponse)
            )
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)

            val result =
                networkClient.get(baseUrl, parameters, headers, DummyResponse::class)

            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientResponseSuccess::class.java)
            val success = result as NetworkClientResponse.NetworkClientResponseSuccess
            assertThat(success.code).isEqualTo(200)
            assertThat(success.data).isEqualTo(DummyResponse("success"))
            assertThat(dummyLogger.logs).contains("GET request to $baseUrl")
        } finally {
            mockWebServer.shutdown()
        }
    }

    @Test
    fun `post returns NetworkClientResponseSuccess on valid response`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            val jsonResponse = """{"content": "created"}"""
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(201)
                    .setHeader("Content-Type", "application/json")
                    .setBody(jsonResponse)
            )
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)
            val bodyContent = "post body"

            val result = networkClient.post(
                baseUrl,
                bodyContent,
                headers,
                DummyResponse::class
            )

            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientResponseSuccess::class.java)
            val success = result as NetworkClientResponse.NetworkClientResponseSuccess
            assertThat(success.code).isEqualTo(201)
            assertThat(success.data).isEqualTo(DummyResponse("created"))
            assertThat(dummyLogger.logs).contains("POST request to $baseUrl")
        } finally {
            mockWebServer.shutdown()
        }
    }

    @Test
    fun `put returns NetworkClientResponseSuccess on valid response`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            val jsonResponse = """{"content": "updated"}"""
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setHeader("Content-Type", "application/json")
                    .setBody(jsonResponse)
            )
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)
            val bodyContent = "put body"

            val result = networkClient.put(
                baseUrl,
                bodyContent,
                headers,
                DummyResponse::class
            )

            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientResponseSuccess::class.java)
            val success = result as NetworkClientResponse.NetworkClientResponseSuccess
            assertThat(success.code).isEqualTo(200)
            assertThat(success.data).isEqualTo(DummyResponse("updated"))
            assertThat(dummyLogger.logs).contains("PUT request to $baseUrl")
        } finally {
            mockWebServer.shutdown()
        }
    }

    @Test
    fun `get returns NetworkClientResponseError when exception occurs`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()
            mockWebServer.shutdown()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)

            val result =
                networkClient.get(baseUrl, parameters, headers, DummyResponse::class)
            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientError::class.java)
            val error = result as NetworkClientResponse.NetworkClientError
            assertThat(error.code).isEqualTo(-1)
            when (val message = error.message) {
                is UiText.DynamicText -> assertThat(message.text).isNotEmpty()
                else -> throw AssertionError("Expected DynamicText")
            }
            assertThat(dummyLogger.logs.any { it.contains("Error:") }).isTrue()
        } finally {
            // Já desligado
        }
    }

    @Test
    fun `post returns NetworkClientResponseError when exception occurs`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()
            mockWebServer.shutdown()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)

            val result =
                networkClient.post(baseUrl, "body", headers, DummyResponse::class)
            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientError::class.java)
            val error = result as NetworkClientResponse.NetworkClientError
            assertThat(error.code).isEqualTo(-1)
            when (val message = error.message) {
                is UiText.DynamicText -> assertThat(message.text).isNotEmpty()
                else -> throw AssertionError("Expected DynamicText")
            }
            assertThat(dummyLogger.logs.any { it.contains("Error:") }).isTrue()
        } finally {
            // Já desligado
        }
    }

    @Test
    fun `put returns NetworkClientResponseError when exception occurs`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()
            mockWebServer.shutdown()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)

            val result =
                networkClient.put(baseUrl, "body", headers, DummyResponse::class)
            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientError::class.java)
            val error = result as NetworkClientResponse.NetworkClientError
            assertThat(error.code).isEqualTo(-1)
            when (val message = error.message) {
                is UiText.DynamicText -> assertThat(message.text).isNotEmpty()
                else -> throw AssertionError("Expected DynamicText")
            }
            assertThat(dummyLogger.logs.any { it.contains("Error:") }).isTrue()
        } finally {
            // Já desligado
        }
    }

    @Test
    fun `delete returns NetworkClientResponseError when exception occurs`() = runTest {
        val mockWebServer = MockWebServer()
        try {
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/").toString()
            mockWebServer.shutdown()

            val dummyLogger = DummyLogger()
            val client = createHttpClient()
            val networkClient = KtorNetworkClient(client, dummyLogger)

            val result = networkClient.delete(baseUrl, headers, DummyResponse::class)
            assertThat(result).isInstanceOf(NetworkClientResponse.NetworkClientError::class.java)
            val error = result as NetworkClientResponse.NetworkClientError
            assertThat(error.code).isEqualTo(-1)
            when (val message = error.message) {
                is UiText.DynamicText -> assertThat(message.text).isNotEmpty()
                else -> throw AssertionError("Expected DynamicText")
            }
            assertThat(dummyLogger.logs.any { it.contains("Error:") }).isTrue()
        } finally {
            // Já desligado
        }
    }
}
