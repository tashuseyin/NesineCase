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
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : PostRepository {
    override fun getApiPostListAndSaveDB(): Flow<Resource<List<PostUIModel>>> = flow {
        try {
            val posts = remoteDataSource.getPosts().map { it.toUIModel() }
            localDataSource.insertPostList(posts.map { it.toEntity() })
            emit(Resource.Success(posts))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun getAllPostsFromDB(): Flow<Resource<List<PostUIModel>>> = flow {
        try {
            val posts = localDataSource.getAllPosts()
            emit(Resource.Success(posts))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun deletePost(id: Int): Int = localDataSource.deletePost(id)
    override suspend fun updatePost(post: PostUIModel): Int =
        localDataSource.updatePost(post.title, post.body, post.id)
}