package com.example.nesinecase.domain.use_case

import com.example.nesinecase.di.IoDispatcher
import com.example.nesinecase.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPostListAndSaveDBUseCase @Inject constructor(
    private val postRepository: PostRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
}