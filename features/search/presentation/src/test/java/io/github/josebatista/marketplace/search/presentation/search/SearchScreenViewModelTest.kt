package io.github.josebatista.marketplace.search.presentation.search

import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.core.logging.Logger
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: SearchScreenViewModel

    private val logger: Logger = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchScreenViewModel(logger)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `onQueryChange should update state with new query`() = runTest {
        val novaQuery = "consulta teste"
        viewModel.onEvent(SearchScreenEvent.OnQueryChange(novaQuery))

        val currentState: SearchScreenState = viewModel.state.value
        assertThat(currentState.query).isEqualTo(novaQuery)
    }

    @Test
    fun `onSearch with query shorter than minimum should send error event and appropriate log`() =
        runTest {
            val queryCurta = "ab"
            viewModel.onEvent(SearchScreenEvent.OnSearch(queryCurta))
            advanceUntilIdle()

            verify { logger.sendLog("Search query [$queryCurta] is too short") }

            when (val event = viewModel.uiEvent.first()) {
                is SearchUiEvent.ShowError -> {
                    assertThat(event.message).isInstanceOf(UiText.StringResource::class.java)
                }

                else -> throw AssertionError("Esperado evento ShowError, mas foi recebido: $event")
            }
        }

    @Test
    fun `onSearch with valid query should send search event and appropriate log`() = runTest {
        val queryValida = "abc"
        viewModel.onEvent(SearchScreenEvent.OnSearch(queryValida))
        advanceUntilIdle()

        verify { logger.sendLog("Search query [$queryValida] is valid") }

        when (val event = viewModel.uiEvent.first()) {
            is SearchUiEvent.Search -> {
                assertThat(event.query).isEqualTo(queryValida)
            }

            else -> throw AssertionError("Esperado evento Search, mas foi recebido: $event")
        }
    }
}
