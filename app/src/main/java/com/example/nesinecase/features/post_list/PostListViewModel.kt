package com.example.nesinecase.features.post_list

import androidx.lifecycle.viewModelScope
import com.example.nesinecase.core.base.BaseViewModel
import com.example.nesinecase.core.util.Result
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.use_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel() {

    private val _posts: MutableStateFlow<List<PostUIModel>> = MutableStateFlow(emptyList())
    val posts: StateFlow<List<PostUIModel>> = _posts.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase.execute().collectViewModel { result ->
                when (result) {
                    is Result.Success -> {
                        _posts.value = result.data
                    }
                    is Result.Error -> {}
                }
            }
        }
    }
}