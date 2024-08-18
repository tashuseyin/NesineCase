package com.example.nesinecase.core.base

import com.example.nesinecase.core.util.Result
import kotlinx.coroutines.flow.Flow

interface BaseUseCase {
    interface FlowableUseCase<in P, out T> : BaseUseCase {
        fun execute(params: P): Flow<Result<T>>
    }

    interface FlowableUseCaseNoParams<out T> : BaseUseCase {
        fun execute(): Flow<Result<T>>
    }
}