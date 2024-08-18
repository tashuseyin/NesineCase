package com.example.nesinecase.domain.repository

import com.example.nesinecase.domain.model.PostUIModel

interface PostRepository {
    suspend fun getPosts(): List<PostUIModel>
}