package com.example.nesinecase.domain.use_case

import android.util.Log
import com.example.nesinecase.core.util.Result
import com.example.nesinecase.di.IoDispatcher
import com.example.nesinecase.domain.model.PostUIModel
import com.example.nesinecase.domain.repository.PostRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    fun execute(): Flow<Result<List<PostUIModel>>> = flow {
        emit(postRepository.getPosts())
    }.map {
        Log.i("SUCCESS","SUCCESS")
        Result.Success(it) }
        .catch {
            Log.i("ERROR","ERROR")
            Result.Error(it.fillInStackTrace())
        }
        .flowOn(dispatcher)
}


