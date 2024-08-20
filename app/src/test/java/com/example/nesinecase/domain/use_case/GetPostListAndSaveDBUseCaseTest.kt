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


class GetPostListAndSaveDBUseCaseTest {
    private val postRepository: PostRepository = mockk()
    private val getPostListAndSaveDBUseCase =  GetPostListAndSaveDBUseCase(postRepository, Dispatchers.IO)

    @Test
    fun `execute returns get post list and save database on success`() = runBlocking {
        val postList = List(30) { mockPostUIModel(it) }
        coEvery { postRepository.getApiPostListAndSaveDB() } returns flowOf(Resource.Success(postList))
        val actualResult = getPostListAndSaveDBUseCase.execute().single()

        coVerify(exactly = 1) { postRepository.getApiPostListAndSaveDB() }
        assert(actualResult is Resource.Success)
        Assert.assertEquals(postList, (actualResult as Resource.Success).data)
    }

    @Test
    fun `execute returns error result on exception`() = runBlocking {
        coEvery { postRepository.getApiPostListAndSaveDB() } returns flowOf(Resource.Error(Exception()))
        val actualResult = getPostListAndSaveDBUseCase.execute().single()

        val expectedErrorResult = Resource.Error(Exception())

        coVerify(exactly = 1) { postRepository.getApiPostListAndSaveDB() }
        assert(actualResult is Resource.Error)
        Assert.assertEquals(
            expectedErrorResult.error.message,
            (actualResult as Resource.Error).error.message
        )
    }
}