package com.example.nesinecase.features.post_list

import com.example.nesinecase.domain.model.PostUIModel

data class PostListUIState(
    val posts: List<PostUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)