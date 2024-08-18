package com.example.nesinecase.domain.use_case

import com.example.nesinecase.core.base.BaseUseCase
import com.example.nesinecase.core.util.Resource
import com.example.nesinecase.di.IoDispatcher
import com.example.nesinecase.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase.FlowableUseCase<Int, Boolean> {

    override fun execute(params: Int): Flow<Resource<Boolean>> = flow {
        try {
            val response = postRepository.deletePost(params)
            emit(Resource.Success(response > 0))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(dispatcher)
}