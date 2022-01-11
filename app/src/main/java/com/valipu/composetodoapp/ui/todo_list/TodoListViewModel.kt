package com.valipu.composetodoapp.ui.todo_list

import androidx.compose.material.Snackbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valipu.composetodoapp.data.Todo
import com.valipu.composetodoapp.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    private var deletedTodo: Todo? = null

    val todoListUiState: MutableStateFlow<TodoListUiState> = MutableStateFlow(TodoListUiState())

    fun fetchAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.getAllTodos().collect {
                todoListUiState.value = todoListUiState.value.copy(todos = it)
            }
        }
    }

    fun doneTodo(todo: Todo, done: Boolean) {
        viewModelScope.launch {
            todoRepository.updateTodo(todo.copy(done = done))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            deletedTodo = todo
            todoRepository.deleteTodo(todo)
            addUiEvent(UiEvent.Snackbar(SnackbarMessages.DELETE, action = "Undo"))
        }
    }

    fun undoDelete() {
        val todo: Todo = deletedTodo ?: return
        viewModelScope.launch {
            todoRepository.insertTodo(todo)
            deletedTodo = null
        }
    }

    fun navigateTodoAdd() {
        addUiEvent(UiEvent.Navigate(NavigationRoutes.ADD))
    }

    fun navigateTodoEdit(todo: Todo) {
        addUiEvent(UiEvent.Navigate(NavigationRoutes.ADD + "?todoId=${todo.id}"))
    }

    fun navigateTodoDetail() {
        addUiEvent(UiEvent.Navigate(NavigationRoutes.TODO_DETAIL))
    }

    private fun addUiEvent(msg: UiEvent) {
        val messages = todoListUiState.value.messages + msg
        todoListUiState.value = todoListUiState.value.copy(messages = messages)
    }

    fun consumeUiEvent(id: Long) {
        val messages = todoListUiState.value.messages
        todoListUiState.update {
            it.copy(messages = messages.filterNot { msg ->  msg.id == id })
        }
    }
}