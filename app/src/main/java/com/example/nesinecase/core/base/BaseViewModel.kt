package com.example.nesinecase.core.base

import androidx.lifecycle.ViewModel
import com.example.nesinecase.core.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _isShowLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isShowLoading.asStateFlow()

    private val _isError: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val isError: StateFlow<Throwable?> = _isError.asStateFlow()


    suspend fun <T> Flow<Result<T>>.collectViewModel(collector: FlowCollector<Result<T>>) {
        onStart { _isShowLoading.value = true }
        this.collect {
            _isShowLoading.value = false
            when (it) {
                is Result.Success -> {
                    collector.emit(it)
                }
                is Result.Error -> {
                    collector.emit(it)
                }
            }
        }
    }
}