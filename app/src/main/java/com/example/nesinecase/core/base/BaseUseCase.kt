package com.example.nesinecase.core.base

import com.example.nesinecase.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface BaseUseCase {
    interface FlowableUseCase<in P, out T> : BaseUseCase {
        fun execute(params: P): Flow<Resource<T>>
    }

    interface FlowableUseCaseNoParams<out T> : BaseUseCase {
        fun execute(): Flow<Resource<T>>
    }
}