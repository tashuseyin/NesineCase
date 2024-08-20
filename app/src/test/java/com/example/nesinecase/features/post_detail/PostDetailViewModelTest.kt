package com.example.nesinecase.features.post_detail

import com.example.nesinecase.CoroutineTestRule
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.use_case.UpdatePostUseCase
import com.example.nesinecase.model.mockPostUIModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostDetailViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val updatePostUseCase: UpdatePostUseCase = mockk()
    private val postDetailViewModel: PostDetailViewModel = PostDetailViewModel(updatePostUseCase)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `test updatePost success sets _isUpdatePost to true`() = runBlocking {
        val postItem = mockPostUIModel(1)
        coEvery { updatePostUseCase.execute(postItem) } returns flowOf(Resource.Success(true))

        postDetailViewModel.updatePost(postItem)
        Assert.assertTrue(postDetailViewModel.isUpdatePost.value == true)
        coVerify(exactly = 1) { updatePostUseCase.execute(postItem) }
    }

    @Test
    fun `test updatePost error sets _isUpdatePost to false`() = runBlocking {
        val postItem = mockPostUIModel(1)
        coEvery { updatePostUseCase.execute(postItem) } returns flowOf(Resource.Error(Exception()))

        postDetailViewModel.updatePost(postItem)
        Assert.assertTrue(postDetailViewModel.isUpdatePost.value == false)
        coVerify(exactly = 1) { updatePostUseCase.execute(postItem) }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}