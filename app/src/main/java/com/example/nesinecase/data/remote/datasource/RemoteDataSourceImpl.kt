package com.example.nesinecase.data.remote.datasource

import com.example.nesinecase.data.remote.response.PostResponse
import com.example.nesinecase.data.remote.service.PostApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: PostApiService
): RemoteDataSource {

    override suspend fun getPosts(): List<PostResponse> {
        return apiService.getPosts()
    }
}