package com.example.nesinecase.data.repository

import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.data.local.datasource.LocalDataSource
import com.example.nesinecase.data.local.mappers.toEntity
import com.example.nesinecase.data.remote.datasource.RemoteDataSource
import com.example.nesinecase.data.remote.mapper.toUIModel
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PostRepository {
    override fun getApiPostListAndSaveDB(): Flow<Resource<List<PostUIModel>>> = flow {
        try {
            val posts = remoteDataSource.getPosts().map { it.toUIModel() }
            localDataSource.insertPostList(posts.map { it.toEntity() })
            localDataSource.getAllPosts().map {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
    override suspend fun getAllPostsFromDB(): Flow<List<PostUIModel>> = localDataSource.getAllPosts()
    override suspend fun deletePost(id: Int): Int = localDataSource.deletePost(id)
    override suspend fun updatePost(post: PostUIModel): Int = localDataSource.updatePost(post.title, post.body, post.id)
}