package com.example.nesinecase.features.post_list

import com.example.nesinecase.domain.model.PostUIModel

data class PostListUIState(
    val isLoading: Boolean = false,
    val posts: List<PostUIModel> = emptyList()
)