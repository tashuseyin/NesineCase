package com.example.nesinecase.data.local.mappers

import com.example.nesinecase.model.mockPostEntity
import com.example.nesinecase.model.mockPostUIModel
import org.junit.Assert
import org.junit.Test

class LocalMappersTest {
    @Test
    fun `test PostUIModel to PostEntity mapping`() {
        val postUIModel = mockPostUIModel(0)
        val postEntity = postUIModel.toEntity()

        Assert.assertEquals(postUIModel.id, postEntity.id)
        Assert.assertEquals(postUIModel.userId, postEntity.userId)
        Assert.assertEquals(postUIModel.title, postEntity.title)
        Assert.assertEquals(postUIModel.body, postEntity.body)
    }

    @Test
    fun `test PostEntity to PostUIModel mapping`() {
        val postEntity =  mockPostEntity(0)
        val postUIModel = postEntity.toUIModel()

        Assert.assertEquals(postEntity.id, postUIModel.id)
        Assert.assertEquals(postEntity.userId, postUIModel.userId)
        Assert.assertEquals(postEntity.title, postUIModel.title)
        Assert.assertEquals(postEntity.body, postUIModel.body)
    }
}