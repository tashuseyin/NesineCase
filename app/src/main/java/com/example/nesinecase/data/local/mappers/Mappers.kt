package com.example.nesinecase.data.local.mappers

import com.example.nesinecase.data.local.entity.PostEntity
import com.example.nesinecase.domain.model.PostUIModel

fun PostEntity.toUIModel(): PostUIModel {
    return PostUIModel(
        id = id,
        body = body,
        title = title,
        userId = userId
    )
}


fun PostUIModel.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        body = body,
        title = title,
        userId = userId
    )
}

