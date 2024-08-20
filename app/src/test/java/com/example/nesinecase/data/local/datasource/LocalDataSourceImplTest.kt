package com.example.nesinecase.data.local.datasource

import com.example.nesinecase.data.local.database.PostDao
import com.example.nesinecase.data.local.mappers.toEntity
import com.example.nesinecase.model.mockPostUIModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class LocalDataSourceImplTest {
    private val postDao: PostDao = mockk()
    private val localDataSourceImpl = LocalDataSourceImpl(postDao)

    @Test
    fun insertPostList() = runBlocking {
        val mockPostList = List(10) { mockPostUIModel(it) }.map { it.toEntity() }
        coEvery { postDao.insertPostList(any()) } returns Unit
        localDataSourceImpl.insertPostList(mockPostList)
        coVerify(exactly = 1) { postDao.insertPostList(mockPostList) }
    }

    @Test
    fun getAllPosts() = runBlocking {
        val mockPostList = List(10) { mockPostUIModel(it) }
        coEvery { postDao.getAllPosts() } returns mockPostList
        val actualResult = localDataSourceImpl.getAllPosts()
        coVerify(exactly = 1) { postDao.getAllPosts() }
        Assert.assertEquals(mockPostList, actualResult)
    }

    @Test
    fun deletePost() = runBlocking {
        val deletePostItem = mockPostUIModel(1)
        coEvery { postDao.deletePost(any()) } returns deletePostItem.id
        val actualResult = localDataSourceImpl.deletePost(deletePostItem.id)
        coVerify(exactly = 1) { postDao.deletePost(deletePostItem.id) }
        Assert.assertEquals(deletePostItem.id, actualResult)
    }

    @Test
    fun updatePost() = runBlocking {
        val updatePostItem = mockPostUIModel(1)
        coEvery { postDao.updatePost(any(), any(), any()) } returns updatePostItem.id
        val actualResult = localDataSourceImpl.updatePost(updatePostItem.title, updatePostItem.body, updatePostItem.id)
        coVerify(exactly = 1) { postDao.updatePost(updatePostItem.title, updatePostItem.body, updatePostItem.id) }
        Assert.assertEquals(updatePostItem.id, actualResult)
    }
}