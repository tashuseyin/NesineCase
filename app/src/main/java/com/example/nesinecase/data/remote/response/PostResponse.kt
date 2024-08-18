package com.example.nesinecase.data.remote.response

data class PostResponse(
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int,
    val imageUrl: String
)