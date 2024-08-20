package com.example.nesinecase.model

import com.example.nesinecase.data.local.entity.PostEntity
import com.example.nesinecase.data.remote.response.PostResponse
import com.example.nesinecase.domain.model.PostUIModel


fun mockPostUIModel(id: Int): PostUIModel = PostUIModel(
    id = id,
    userId = id,
    title = "title $id",
    body = "body $id"
)

fun mockPostResponse(id: Int): PostResponse = PostResponse(
    id = id,
    userId = id,
    title = "title $id",
    body = "body $id"
)

fun mockPostEntity(id: Int): PostEntity = PostEntity(
    id = id,
    userId = id,
    title = "title $id",
    body = "body $id"
)



