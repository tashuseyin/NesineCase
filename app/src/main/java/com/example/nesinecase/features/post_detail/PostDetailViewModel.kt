package com.example.nesinecase.features.post_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.use_case.UpdatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val updatePostUseCase: UpdatePostUseCase
) : ViewModel() {

    private val _isUpdatePost: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isUpdatePost: StateFlow<Boolean?> = _isUpdatePost.asStateFlow()

    fun updatePost(postItem: PostUIModel) {
        viewModelScope.launch {
            updatePostUseCase.execute(postItem).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _isUpdatePost.value = true
                    }

                    is Resource.Error -> {
                        _isUpdatePost.value = false
                    }
                }
            }
        }
    }
}