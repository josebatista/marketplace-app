package io.github.josebatista.marketplace.logging

import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.core.logging.Logger
import io.github.josebatista.marketplace.core.logging.StdLoggerImpl
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class StdLoggerImplTest {
    private val originalOut: PrintStream = System.out
    private lateinit var outputStreamCaptor: ByteArrayOutputStream

    @Before
    fun setUp() {
        outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @After
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `sendLog should print the formatted message correctly`() {
        val logger: Logger = StdLoggerImpl()
        val mensagemTeste = "Hello, Logger!"

        logger.sendLog(mensagemTeste)

        val expectedOutput = "[LOGGER]: $mensagemTeste" + System.lineSeparator()
        assertThat(outputStreamCaptor.toString()).isEqualTo(expectedOutput)
    }
}
