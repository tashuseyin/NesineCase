package com.example.nesinecase.data.remote.datasource

import com.example.nesinecase.data.remote.response.PostResponse

interface RemoteDataSource {
    suspend fun getPosts(): List<PostResponse>
}