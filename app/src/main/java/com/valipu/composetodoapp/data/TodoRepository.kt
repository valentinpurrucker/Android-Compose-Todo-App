package com.valipu.composetodoapp.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getAllTodos(): Flow<List<Todo>>

    suspend fun getTodoBy(id: Int): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)
}