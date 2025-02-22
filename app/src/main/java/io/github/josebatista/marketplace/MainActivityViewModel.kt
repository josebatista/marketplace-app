package io.github.josebatista.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    internal fun onEvent() {
        viewModelScope.launch {
            searchUseCase("query")
        }
    }
}
