package com.example.nesinecase.core.extensions

import androidx.navigation.NavController

fun <T> NavController.setDataPreviousScreen(key: String, value: T) {
    this.previousBackStackEntry?.savedStateHandle?.set(key, value)
}


fun <T> NavController.getDataCurrentScreen(key: String): T? {
    return this.previousBackStackEntry?.savedStateHandle?.get<T>(key)
}

