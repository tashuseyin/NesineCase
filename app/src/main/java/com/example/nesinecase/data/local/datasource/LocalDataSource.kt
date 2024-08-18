package com.example.nesinecase.data.local.datasource

import com.example.nesinecase.data.local.entity.PostEntity
import com.example.nesinecase.domain.model.PostUIModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertPostList(posts: List<PostEntity>)
    fun getAllPosts(): Flow<List<PostUIModel>>
    suspend fun deletePost(id: Int): Int
    suspend fun updatePost(title: String, body: String, id: Int): Int
}