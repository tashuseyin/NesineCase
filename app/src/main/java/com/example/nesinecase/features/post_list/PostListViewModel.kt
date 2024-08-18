package com.example.nesinecase.features.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nesinecase.core.util.Result
import com.example.nesinecase.domain.use_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<PostListUIState> = MutableStateFlow(PostListUIState())
    private val uiState: StateFlow<PostListUIState> = _uiState.asStateFlow()



    fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase.execute().collect { result ->
                when (result) {
                   is Result.Success -> {
                       _uiState.update { state ->
                           state.copy(posts = result.data)
                       }
                    }
                    else -> {}
                }
            }
        }
    }
}