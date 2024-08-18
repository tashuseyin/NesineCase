package com.example.nesinecase.data.local.datasource

import com.example.nesinecase.data.local.database.PostDao
import com.example.nesinecase.data.local.entity.PostEntity
import com.example.nesinecase.domain.model.PostUIModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val postDao: PostDao
) : LocalDataSource {
    override suspend fun insertPostList(posts: List<PostEntity>) = postDao.insertPostList(posts)
    override fun getAllPosts(): Flow<List<PostUIModel>> = postDao.getAllPosts()
    override suspend fun deletePost(id: Int): Int = postDao.deletePost(id)
    override suspend fun updatePost(title: String, body: String, id: Int): Int = postDao.updatePost(title, body, id)
}