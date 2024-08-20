package com.example.nesinecase.features.post_list

import com.example.nesinecase.CoroutineTestRule
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.use_case.DeletePostUseCase
import com.example.nesinecase.domain.use_case.GetAllPostsFromDBUseCase
import com.example.nesinecase.domain.use_case.GetPostListAndSaveDBUseCase
import com.example.nesinecase.model.mockPostUIModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostListViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val getPostListAndSaveDBUseCase: GetPostListAndSaveDBUseCase = mockk()
    private val getAllPostsFromDBUseCase: GetAllPostsFromDBUseCase = mockk { justRun { execute() } }
    private val deletePostUseCase: DeletePostUseCase = mockk()
    private lateinit var postListViewModel: PostListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        postListViewModel = PostListViewModel(getPostListAndSaveDBUseCase, getAllPostsFromDBUseCase, deletePostUseCase)
    }

    @Test
    fun `getAllPostsFromDB with not empty data success updates uiState with posts`() = runBlocking {
        val postList = List(30) { mockPostUIModel(it) }
        coEvery { getAllPostsFromDBUseCase.execute() } returns flowOf(Resource.Success(List(30) { mockPostUIModel(it) }))
        postListViewModel.getAllPostsFromDB()
        val exceptedUIState = PostListUIState(posts = postList)
        assertEquals(exceptedUIState, postListViewModel.uiState.value)
    }

    @Test
    fun `getAllPostsFromDB with empty data calls getApiPostListAndSaveDB success and updates uiState`() = runBlocking {
        val postList = List(30) { mockPostUIModel(it) }
        coEvery { getAllPostsFromDBUseCase.execute() } returns flowOf(Resource.Success(emptyList()))
        coEvery { getPostListAndSaveDBUseCase.execute() } returns flowOf(Resource.Success(postList))
        postListViewModel.getAllPostsFromDB()
        val exceptedUIState = PostListUIState(posts = postList)
        assertEquals(exceptedUIState, postListViewModel.uiState.value)
    }

    @Test
    fun `getAllPostsFromDB with empty data calls getApiPostListAndSaveDB error and updates uiState`() = runBlocking {
        val expectedResource = Resource.Error(Exception())
        coEvery { getAllPostsFromDBUseCase.execute() } returns flowOf(Resource.Success(emptyList()))
        coEvery { getPostListAndSaveDBUseCase.execute() } returns flowOf(expectedResource)
        postListViewModel.getAllPostsFromDB()
        val exceptedUIState = PostListUIState(isError = true, errorMessage = expectedResource.error.message)
        assertEquals(exceptedUIState, postListViewModel.uiState.value)
    }

    @Test
    fun `getAllPostsFromDB error updates uiState with posts`() = runBlocking {
        val expectedResource = Resource.Error(Exception())
        every { getAllPostsFromDBUseCase.execute() } returns flowOf(expectedResource)
        postListViewModel.getAllPostsFromDB()
        val exceptedUIState = PostListUIState(isError = true, errorMessage = expectedResource.error.message)
        assertEquals(exceptedUIState, postListViewModel.uiState.value)
    }


    @Test
    fun `deletePost success updates uiState with posts`() = runBlocking {
        val postList = List(30) { mockPostUIModel(it) }
        val deletedPostItem = postList.first()
        coEvery { getAllPostsFromDBUseCase.execute() } returns flowOf(Resource.Success(postList))
        postListViewModel.getAllPostsFromDB()
        assertEquals(postList, postListViewModel.uiState.value.posts)
        Assert.assertTrue(postListViewModel.uiState.value.posts.contains(deletedPostItem))
        coEvery { deletePostUseCase.execute(deletedPostItem.id) } returns flowOf(Resource.Success(true))
        postListViewModel.deletePost(deletedPostItem)
        Assert.assertFalse(postListViewModel.uiState.value.posts.contains(deletedPostItem))
    }

    @Test
    fun `deletePost error updates uiState with posts`() = runBlocking {
        val postList = List(30) { mockPostUIModel(it) }
        val deletedPostItem = postList.first()
        coEvery { getAllPostsFromDBUseCase.execute() } returns flowOf(Resource.Success(postList))
        postListViewModel.getAllPostsFromDB()
        assertEquals(postList, postListViewModel.uiState.value.posts)
        Assert.assertTrue(postListViewModel.uiState.value.posts.contains(deletedPostItem))
        coEvery { deletePostUseCase.execute(deletedPostItem.id) } returns flowOf(Resource.Error(Exception()))
        postListViewModel.deletePost(deletedPostItem)
        Assert.assertTrue(postListViewModel.uiState.value.posts.contains(deletedPostItem))
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}