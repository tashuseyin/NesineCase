package com.example.nesinecase.domain.use_case

import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.repository.PostRepository
import com.example.nesinecase.model.mockPostUIModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class GetAllPostsFromDBUseCaseTest {
    private val postRepository: PostRepository = mockk()
    private val getAllPostFromDBUseCase = GetAllPostsFromDBUseCase(postRepository, Dispatchers.IO)

    @Test
    fun `execute returns get all posts from database on success`() = runBlocking {
        val postList = List(30) { mockPostUIModel(it) }
        coEvery { postRepository.getAllPostsFromDB() } returns flowOf(Resource.Success(postList))
        val actualResult = getAllPostFromDBUseCase.execute().single()

        coVerify(exactly = 1) { postRepository.getAllPostsFromDB() }
        assert(actualResult is Resource.Success)
        Assert.assertEquals(postList, (actualResult as Resource.Success).data)
    }

    @Test
    fun `execute returns error result on exception`() = runBlocking {
        coEvery { postRepository.getAllPostsFromDB() } returns flowOf(Resource.Error(Exception()))
        val actualResult = getAllPostFromDBUseCase.execute().single()

        val expectedErrorResult = flowOf(Resource.Error(Exception()))

        coVerify(exactly = 1) { postRepository.getAllPostsFromDB() }
        assert(actualResult is Resource.Error)
        Assert.assertEquals(
            expectedErrorResult.single().error.message,
            (actualResult as Resource.Error).error.message
        )
    }
}