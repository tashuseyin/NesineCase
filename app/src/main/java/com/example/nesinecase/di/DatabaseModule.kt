package com.example.nesinecase.di

import android.content.Context
import androidx.room.Room
import com.example.nesinecase.BuildConfig
import com.example.nesinecase.data.local.database.PostDao
import com.example.nesinecase.data.local.database.PostDatabase
import com.example.nesinecase.data.local.datasource.LocalDataSource
import com.example.nesinecase.data.local.datasource.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    companion object {
        @Provides
        @Singleton
        fun providePostDatabase(
            @ApplicationContext context: Context
        ): PostDatabase {
            return Room.databaseBuilder(
                context,
                PostDatabase::class.java,
                BuildConfig.DATABASE_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun providePostDao(postDatabase: PostDatabase): PostDao {
            return postDatabase.postDao()
        }
    }

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}
