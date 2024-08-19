package com.example.nesinecase.data.local.datasource

import com.example.nesinecase.data.local.entity.PostEntity
import com.example.nesinecase.domain.model.PostUIModel

interface LocalDataSource {
    suspend fun insertPostList(posts: List<PostEntity>)
    suspend fun getAllPosts(): List<PostUIModel>
    suspend fun deletePost(id: Int): Int
    suspend fun updatePost(title: String, body: String, id: Int): Int
}