package com.example.nesinecase.domain.use_case

import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.repository.PostRepository
import com.example.nesinecase.model.mockPostUIModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class DeletePostUseCaseTest {
    private val postRepository: PostRepository = mockk()
    private val deletePostUseCase =  DeletePostUseCase(postRepository, Dispatchers.IO)

    @Test
    fun `execute returns delete post on success`() = runBlocking {
        val postItem = mockPostUIModel(1)
        coEvery { postRepository.deletePost(any()) } returns postItem.id
        val actualResult = deletePostUseCase.execute(postItem.id).single()

        coVerify(exactly = 1) { postRepository.deletePost(any()) }
        assert(actualResult is Resource.Success)
        Assert.assertTrue((actualResult as Resource.Success).data)
    }

    @Test
    fun `execute returns error result on exception`() = runBlocking {
        val postItem = mockPostUIModel(1)
        coEvery { postRepository.deletePost(any()) } throws Exception()
        val actualResult = deletePostUseCase.execute(postItem.id).single()

        val expectedErrorResult = Resource.Error(Exception())

        coVerify(exactly = 1) { postRepository.deletePost(any()) }
        assert(actualResult is Resource.Error)
        Assert.assertEquals(
            expectedErrorResult.error.message,
            (actualResult as Resource.Error).error.message
        )
    }
}