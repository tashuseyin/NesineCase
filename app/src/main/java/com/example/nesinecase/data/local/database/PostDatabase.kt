package com.example.nesinecase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nesinecase.data.local.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}