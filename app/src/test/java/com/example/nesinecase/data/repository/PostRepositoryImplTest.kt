package com.example.nesinecase.data.repository

import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.data.local.datasource.LocalDataSource
import com.example.nesinecase.data.remote.datasource.RemoteDataSource
import com.example.nesinecase.data.remote.mapper.toUIModel
import com.example.nesinecase.model.mockPostResponse
import com.example.nesinecase.model.mockPostUIModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class PostRepositoryImplTest {

    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val postRepositoryImpl = PostRepositoryImpl(remoteDataSource, localDataSource)

    @Test
    fun `test getApiPostListAndSaveDB return on success`() = runBlocking {
        val mockPostResponse = List(10) { mockPostResponse(it) }
        coEvery { remoteDataSource.getPosts() } returns mockPostResponse
        coEvery { localDataSource.insertPostList(any()) } returns Unit
        val actualResult = postRepositoryImpl.getApiPostListAndSaveDB().single()
        coVerify(exactly = 1) { remoteDataSource.getPosts() }
        coVerify(exactly = 1) { localDataSource.insertPostList(any()) }
        Assert.assertTrue(actualResult is Resource.Success)
        Assert.assertEquals(mockPostResponse.map { it.toUIModel() }, (actualResult as Resource.Success).data)
    }


    @Test
    fun `test getApiPostListAndSaveDB return on error`() = runBlocking {
        val mockPostResponse = List(10) { mockPostResponse(it) }
        coEvery { remoteDataSource.getPosts() } throws Exception()
        coEvery { localDataSource.insertPostList(any()) } returns Unit
        val actualResult = postRepositoryImpl.getApiPostListAndSaveDB().single()
        coVerify(exactly = 1) { remoteDataSource.getPosts() }
        coVerify(exactly = 1) { localDataSource.insertPostList(any()) }
        Assert.assertTrue(actualResult is Resource.Success)
        Assert.assertEquals(mockPostResponse.map { it.toUIModel() }, (actualResult as Resource.Success).data)
    }

    @Test
    fun `test getAllPostsFromDB return on success`() = runBlocking {
        val mockPostList = List(10) { mockPostUIModel(it) }
        coEvery { localDataSource.getAllPosts() } returns mockPostList
        val actualResult = postRepositoryImpl.getAllPostsFromDB().single()
        coVerify(exactly = 1) { localDataSource.getAllPosts() }
        Assert.assertTrue(actualResult is Resource.Success)
        Assert.assertEquals(mockPostList, (actualResult as Resource.Success).data)
    }

    @Test
    fun `test getAllPostsFromDB return on error`() = runBlocking {
        coEvery { localDataSource.getAllPosts() } throws Exception()
        val actualResult = postRepositoryImpl.getAllPostsFromDB().single()
        coVerify(exactly = 1) { localDataSource.getAllPosts() }
        val expectedErrorResult = Resource.Error(Exception())
        assert(actualResult is Resource.Error)
        Assert.assertEquals(
            expectedErrorResult.error.message,
            (actualResult as Resource.Error).error.message
        )
    }

    @Test
    fun `test deletePost`() = runBlocking {
        val deletePostItem = mockPostUIModel(1)
        coEvery { localDataSource.deletePost(any()) } returns deletePostItem.id
        val actualResult = postRepositoryImpl.deletePost(deletePostItem.id)
        coVerify(exactly = 1) { localDataSource.deletePost(deletePostItem.id) }
        Assert.assertEquals(deletePostItem.id, actualResult)
    }

    @Test
    fun `test updatePost`() = runBlocking {
        val updatePostItem = mockPostUIModel(1)
        coEvery { localDataSource.updatePost(any(), any(), any()) } returns updatePostItem.id
        val actualResult = postRepositoryImpl.updatePost(updatePostItem)
        coVerify(exactly = 1) {
            localDataSource.updatePost(
                updatePostItem.title,
                updatePostItem.body,
                updatePostItem.id
            )
        }
        Assert.assertEquals(updatePostItem.id, actualResult)
    }
}