package com.valipu.composetodoapp.ui.todo_list

import com.valipu.composetodoapp.data.Todo
import java.util.*

data class TodoListUiState(
    val todos: List<Todo> = emptyList(),
    val messages: List<UiEvent> = emptyList(),
    val isTodoDetailView: Boolean = false
)


sealed class UiEvent(open val id: Long) {
    class PopBackStack(override val id: Long = UUID.randomUUID().mostSignificantBits) : UiEvent(id)
    data class Navigate(val route: String, override val id: Long = UUID.randomUUID().mostSignificantBits) : UiEvent(id)
    data class Snackbar(val msg: String, val action: String? = null, override val id: Long = UUID.randomUUID().mostSignificantBits) : UiEvent(id)
}


object NavigationRoutes {
    const val ADD = "add"
    const val TODO_LIST = "todo_list"
    const val TODO_DETAIL = "todo_detail"
}

object SnackbarMessages {
    const val DELETE = "Todo deleted"
    const val SAVED = "Todo saved"
    const val TITLE_BLANK = "Title must not be empty"
}