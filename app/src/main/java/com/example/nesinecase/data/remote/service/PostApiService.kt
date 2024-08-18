package com.example.nesinecase.data.remote.service

import com.example.nesinecase.data.remote.response.PostResponse
import retrofit2.http.GET

interface PostApiService {
    @GET("/posts")
    suspend fun getPosts(): List<PostResponse>
}