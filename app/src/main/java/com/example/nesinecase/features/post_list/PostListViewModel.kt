package com.example.nesinecase.features.post_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.use_case.GetAllPostsFromDBUseCase
import com.example.nesinecase.domain.use_case.GetPostListAndSaveDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostListAndSaveDBUseCase: GetPostListAndSaveDBUseCase,
    private val getAllPostsFromDBUseCase: GetAllPostsFromDBUseCase
) : ViewModel() {

    private val _posts: MutableStateFlow<List<PostUIModel>> = MutableStateFlow(emptyList())
    val posts: StateFlow<List<PostUIModel>> = _posts.asStateFlow()

    init {
        getAllPostsFromDB()
    }

    private fun getAllPostsFromDB() {
        viewModelScope.launch {
            getAllPostsFromDBUseCase.execute().collect {
                when (it) {
                    is Resource.Success -> {
                        _posts.value = it.data
                    }

                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun getApiPostListAndSaveDB() {
        viewModelScope.launch {
            getPostListAndSaveDBUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}