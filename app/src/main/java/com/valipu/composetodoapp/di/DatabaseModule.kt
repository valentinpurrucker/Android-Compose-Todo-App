package com.valipu.composetodoapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.valipu.composetodoapp.data.TodoDao
import com.valipu.composetodoapp.data.TodoDatabase
import com.valipu.composetodoapp.data.TodoRepository
import com.valipu.composetodoapp.data.TodoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room
            .databaseBuilder(context, TodoDatabase::class.java, "todo_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideTodoDao(db: TodoDatabase): TodoDao {
        return db.todoDao()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(todoDatabase: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(todoDatabase.todoDao())
    }

}