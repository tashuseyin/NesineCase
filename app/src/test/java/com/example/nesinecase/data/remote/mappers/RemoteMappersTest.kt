package com.example.nesinecase.data.remote.mappers

import com.example.nesinecase.data.remote.mapper.toUIModel
import com.example.nesinecase.model.mockPostResponse
import org.junit.Assert
import org.junit.Test

class RemoteMappersTest {

    @Test
    fun `test PostResponse to PostUIModel mapping`() {
        val postResponse = mockPostResponse(0)
        val postUIModel = postResponse.toUIModel()

        Assert.assertEquals(postResponse.id, postUIModel.id)
        Assert.assertEquals(postResponse.userId, postUIModel.userId)
        Assert.assertEquals(postResponse.title, postUIModel.title)
        Assert.assertEquals(postResponse.body, postUIModel.body)
    }
}