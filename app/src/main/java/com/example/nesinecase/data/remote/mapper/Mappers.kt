package com.example.nesinecase.data.remote.mapper

import com.example.nesinecase.data.remote.response.PostResponse
import com.example.nesinecase.domain.model.PostUIModel

fun PostResponse.toUIModel(): PostUIModel {
    return PostUIModel(
        body = this.body,
        id = this.id,
        title = this.title,
        userId = this.userId
    )
}