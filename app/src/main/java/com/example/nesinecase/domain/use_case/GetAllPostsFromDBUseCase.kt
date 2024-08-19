package com.example.nesinecase.domain.use_case

import com.example.nesinecase.core.base.BaseUseCase
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.di.IoDispatcher
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllPostsFromDBUseCase @Inject constructor(
    private val postRepository: PostRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase.FlowableUseCaseNoParams<List<PostUIModel>> {

    override fun execute(): Flow<Resource<List<PostUIModel>>> =
        postRepository.getAllPostsFromDB().flowOn(dispatcher)
}