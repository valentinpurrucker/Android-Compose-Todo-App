package com.valipu.composetodoapp.ui.add_edit_todo

import com.valipu.composetodoapp.data.Todo
import com.valipu.composetodoapp.ui.todo_list.UiEvent

data class AddEditUiState(
    val title: String = "",
    val description: String? = null,
    val todo: Todo? = null,
    val messages: List<UiEvent> = emptyList()
)
