package io.github.josebatista.marketplace.search.presentation.list

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import io.github.josebatista.marketplace.logging.Logger
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ListScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ListScreenViewModel

    private val logger: Logger = mockk(relaxed = true)
    private val searchUseCase: SearchUseCase = mockk(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf("query" to "testQuery"))
        viewModel = ListScreenViewModel(searchUseCase, logger, savedStateHandle)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `init should log correct message with provided query`() {
        verify { logger.sendLog("Iniciando consulta de produtos para a query: testQuery") }
    }

    @Test
    fun `default scrollPosition is initial`() {
        val currentScroll = viewModel.scrollPosition.value
        assertThat(currentScroll.index).isEqualTo(0)
        assertThat(currentScroll.offset).isEqualTo(0)
    }

    @Test
    fun `onEvent should update scrollPosition when OnScrollPositionChange is triggered`() =
        runTest {
            val newIndex = 10
            val newOffset = 150
            val event = ListScreenEvent.OnScrollPositionChange(newIndex, newOffset)
            viewModel.onEvent(event)
            advanceUntilIdle()

            val updatedPosition = viewModel.scrollPosition.value
            assertThat(updatedPosition.index).isEqualTo(newIndex)
            assertThat(updatedPosition.offset).isEqualTo(newOffset)
        }

    @Test
    fun `init with empty query should log empty query message`() {
        val emptyStateHandle = SavedStateHandle(emptyMap<String, String>())
        ListScreenViewModel(searchUseCase, logger, emptyStateHandle)
        verify { logger.sendLog("Iniciando consulta de produtos para a query: ") }
    }
}
