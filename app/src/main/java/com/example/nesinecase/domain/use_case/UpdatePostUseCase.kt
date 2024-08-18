package com.example.nesinecase.domain.use_case

import com.example.nesinecase.core.base.BaseUseCase
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.di.IoDispatcher
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase.FlowableUseCase<PostUIModel, Boolean> {

    override fun execute(params: PostUIModel): Flow<Resource<Boolean>> = flow {
        try {
            val response = postRepository.updatePost(params)
            emit(Resource.Success(response > 0))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(dispatcher)
}