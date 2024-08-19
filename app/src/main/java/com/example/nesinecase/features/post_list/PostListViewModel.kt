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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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
        getAllPostsFromDBUseCase.execute()
            .onStart { _uiState.update { state -> state.copy(isLoading = true) } }
            .onEach { result ->
                handleResourceResult(
                    resource = result,
                    onSuccess = { data ->
                        if (data.isEmpty()) {
                            getApiPostListAndSaveDB()
                        } else {
                            _uiState.update { it.copy(posts = data) }
                        }
                    }
                )
            }.launchIn(viewModelScope)
    }

    private fun getApiPostListAndSaveDB() {
        getPostListAndSaveDBUseCase.execute()
            .onStart { _uiState.update { state -> state.copy(isLoading = true) } }
            .onEach { result ->
                handleResourceResult(
                    resource = result,
                    onSuccess = { data ->
                        _uiState.update { it.copy(posts = data) }
                    }
                )
            }.launchIn(viewModelScope)
    }


    fun deletePost(postItem: PostUIModel) {
        deletePostUseCase.execute(postItem.id).onEach { result ->
            handleResourceResult(
                resource = result,
                onSuccess = {
                    val updateList = uiState.value.posts.toMutableList()
                    updateList.remove(postItem)
                    _uiState.update { it.copy(posts = updateList) }
                }
            )
        }.launchIn(viewModelScope)
    }

    private fun <T> handleResourceResult(
        resource: Resource<T>,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable?) -> Unit = {}
    ) {
        when (resource) {
            is Resource.Success -> {
                onSuccess(resource.data)
                _uiState.update { state -> state.copy(isLoading = false) }
            }

            is Resource.Error -> {
                onError(resource.error)
                _uiState.update { state ->
                    state.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = resource.error.message
                    )
                }
            }
        }
    }
}