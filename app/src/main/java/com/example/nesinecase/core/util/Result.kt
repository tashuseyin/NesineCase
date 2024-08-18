package com.example.nesinecase.core.util

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Error(val error: Throwable) : Result<Nothing>()
}