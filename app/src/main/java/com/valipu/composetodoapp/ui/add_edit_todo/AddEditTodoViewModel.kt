package com.valipu.composetodoapp.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valipu.composetodoapp.data.Todo
import com.valipu.composetodoapp.data.TodoRepository
import com.valipu.composetodoapp.ui.todo_list.SnackbarMessages
import com.valipu.composetodoapp.ui.todo_list.UiEvent
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todoUiState: MutableStateFlow<AddEditUiState> = MutableStateFlow(AddEditUiState())
        private set

    init {
        val todoId = savedStateHandle.get<Int>("todoId") ?: -1
        if (todoId != -1) {
            viewModelScope.launch {
                val todo = todoRepository.getTodoBy(todoId) ?: return@launch
                todoUiState.update {
                    it.copy(
                        title = todo.title,
                        description = todo.description,
                        todo = todo
                    )
                }
            }
        }
    }

    fun setTitle(title: String) {
        todoUiState.update {
            it.copy(title = title)
        }
    }

    fun setDescription(description: String) {
        todoUiState.update {
            it.copy(description = description)
        }
    }

    fun saveTodo() {
        viewModelScope.launch {
            if (todoUiState.value.title.isBlank()) {
                addUiEvent(UiEvent.Snackbar(SnackbarMessages.TITLE_BLANK))
                return@launch
            }
            todoRepository.insertTodo(
                Todo(
                    id = todoUiState.value.todo?.id,
                    title = todoUiState.value.title,
                    description = todoUiState.value.description,
                    done = todoUiState.value.todo?.done ?: false
                )
            )
            addUiEvent(UiEvent.Snackbar(SnackbarMessages.SAVED))
            addUiEvent(UiEvent.PopBackStack())
        }
    }


    private fun addUiEvent(msg: UiEvent) {
        val messages = todoUiState.value.messages + msg
        todoUiState.update {
            it.copy(messages = messages)
        }
    }

    fun consumeUiEvent(id: Long) {
        val messages = todoUiState.value.messages
        todoUiState.update {
            it.copy(messages = messages.filterNot { msg ->  msg.id == id })
        }
    }

}