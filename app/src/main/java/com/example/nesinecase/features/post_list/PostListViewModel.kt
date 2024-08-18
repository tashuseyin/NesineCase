package com.example.nesinecase.features.post_list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.nesinecase.core.base.BaseViewModel
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
) : BaseViewModel() {

    private val _posts: MutableStateFlow<List<PostUIModel>> = MutableStateFlow(emptyList())
    val posts: StateFlow<List<PostUIModel>> = _posts.asStateFlow()

    private val _message: MutableStateFlow<String> = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    init {
        getAllPostsFromDB()
    }

    private fun getAllPostsFromDB() {
        viewModelScope.launch {
            getAllPostsFromDBUseCase.execute().collectViewModel {
                when(it) {
                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            getApiPostListAndSaveDB()
                        } else {
                            _posts.value = it.data
                            _message.value = "DATABASE"
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun getApiPostListAndSaveDB() {
        viewModelScope.launch {
            getPostListAndSaveDBUseCase.execute().collectViewModel { result ->
                when (result) {
                    is Resource.Success -> {
                        _message.value = "NETWORK"
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }

}