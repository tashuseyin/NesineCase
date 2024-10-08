package com.example.nesinecase.domain.repository

import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.model.PostUIModel
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getApiPostListAndSaveDB(): Flow<Resource<List<PostUIModel>>>
    fun getAllPostsFromDB(): Flow<Resource<List<PostUIModel>>>
    suspend fun deletePost(id: Int): Int
    suspend fun updatePost(post: PostUIModel): Int
}