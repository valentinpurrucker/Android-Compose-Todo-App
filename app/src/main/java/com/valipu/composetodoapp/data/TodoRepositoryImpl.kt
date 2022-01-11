package com.valipu.composetodoapp.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val dao: TodoDao) : TodoRepository {

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos()
    }

    override suspend fun getTodoBy(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insert(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.delete(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        dao.update(todo)
    }

}