package com.example.nesinecase.data.remote.datasource

import com.example.nesinecase.data.remote.service.PostApiService
import com.example.nesinecase.model.mockPostResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class RemoteDataSourceImplTest {
    private val apiService: PostApiService = mockk()
    private val remoteDataSourceImpl = RemoteDataSourceImpl(apiService)

    @Test
    fun getPosts() = runBlocking {
        val mockPostResponse = List(10) { mockPostResponse(it) }
        coEvery { apiService.getPosts() } returns mockPostResponse
        val actualResult = remoteDataSourceImpl.getPosts()
        coVerify(exactly = 1) { apiService.getPosts() }
        Assert.assertEquals(mockPostResponse, actualResult)
    }
}