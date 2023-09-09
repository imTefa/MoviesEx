package com.example.movies.presentation.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


abstract class BaseViewModel<U : BaseUiState> : ViewModel() {

    private val _state: MutableStateFlow<BaseUiState> = MutableStateFlow(BaseUiState())
    protected val state = _state.asStateFlow()

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        triggerErrorState(throwable.localizedMessage)
    }

    protected fun triggerUiState(uiState: BaseUiState) {
        viewModelScope.launch {
            _state.value = uiState
        }
    }

    protected fun triggerErrorState(
        errorMessage: String? = null, @StringRes errorResId: Int = R.string.error_common_message
    ) {
        viewModelScope.launch {
            triggerUiState(
                BaseUiState(
                    isError = true, errorMessage = errorMessage, errorResId = errorResId
                )
            )
            delay(100)
            triggerInitialState()
        }
    }

    protected open fun triggerLoadingState() {
        triggerUiState(BaseUiState(isLoading = true))
    }

    protected fun triggerInitialState() {
        triggerUiState(BaseUiState())
    }

    fun loadingState() = state.map { it.isLoading }.distinctUntilChanged()

    @Deprecated("Use errorUiState", ReplaceWith("errorUiState"))
    fun errorState() = state.filter { it.isError }.mapNotNull { it.errorMessage to it.errorResId }

    fun errorUiState() =
        state.filter { it.isError }.mapNotNull { ErrorUiState(it.errorMessage, it.errorResId) }

    abstract fun state(): Flow<U>


    protected fun launchInViewModelScope(launch: suspend (CoroutineScope) -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            launch.invoke(this)
        }
    }
}