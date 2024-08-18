package com.example.nesinecase.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nesinecase.data.local.entity.PostEntity
import com.example.nesinecase.domain.model.PostUIModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostList(posts: List<PostEntity>)

    @Query("SELECT * FROM post_table")
    fun getAllPosts(): Flow<List<PostUIModel>>

    @Query("DELETE FROM post_table WHERE id = :id")
    suspend fun deletePost(id: Int): Int

    @Query("UPDATE post_table SET title = :title, body = :body WHERE id = :id")
    suspend fun updatePost(title: String, body: String, id: Int): Int
}