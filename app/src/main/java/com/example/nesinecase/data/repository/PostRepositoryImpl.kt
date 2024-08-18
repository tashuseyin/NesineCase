package com.example.nesinecase.data.repository

import com.example.nesinecase.data.remote.datasource.RemoteDataSource
import com.example.nesinecase.data.remote.mapper.toUIModel
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PostRepository {
    override suspend fun getPosts(): List<PostUIModel> {
        return remoteDataSource.getPosts().map { it.toUIModel() }
    }
}