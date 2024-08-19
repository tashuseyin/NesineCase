package com.example.nesinecase.features.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.use_case.DeletePostUseCase
import com.example.nesinecase.domain.use_case.GetAllPostsFromDBUseCase
import com.example.nesinecase.domain.use_case.GetPostListAndSaveDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostListAndSaveDBUseCase: GetPostListAndSaveDBUseCase,
    private val getAllPostsFromDBUseCase: GetAllPostsFromDBUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<PostListUIState> = MutableStateFlow(PostListUIState())
    val uiState: StateFlow<PostListUIState> = _uiState.asStateFlow()

    init {
        getAllPostsFromDB()
    }

    fun getAllPostsFromDB() {
        viewModelScope.launch {
            getAllPostsFromDBUseCase.execute()
                .onStart { _uiState.update { state -> state.copy(isLoading = true) } }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data.isEmpty()) {
                                getApiPostListAndSaveDB()
                            } else {
                                _uiState.update { state ->
                                    state.copy(posts = it.data, isLoading = false)
                                }
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    isError = true,
                                    isLoading = false,
                                    errorMessage = it.error.message
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun getApiPostListAndSaveDB() {
        viewModelScope.launch {
            getPostListAndSaveDBUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                isError = true,
                                isLoading = false,
                                errorMessage = result.error.message
                            )
                        }
                    }
                }
            }
        }
    }


    fun deletePost(postItem: PostUIModel) {
        viewModelScope.launch {
            deletePostUseCase.execute(postItem.id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val updateList = uiState.value.posts.toMutableList()
                        updateList.remove(postItem)
                        _uiState.update { state ->
                            state.copy(posts = updateList)
                        }
                    }

                    is Resource.Error -> {}
                }
            }
        }
    }
}