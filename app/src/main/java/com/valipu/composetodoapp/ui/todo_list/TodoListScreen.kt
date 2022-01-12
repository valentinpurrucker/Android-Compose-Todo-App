package com.valipu.composetodoapp.ui.todo_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TodoListScreen(todoViewModel: TodoListViewModel = hiltViewModel(), onNavigate: (String) -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val todos: State<TodoListUiState> = todoViewModel.todoListUiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        todoViewModel.fetchAllTodos()
    }

    LaunchedEffect(key1 = todos.value.messages) {
        when (val msg = todos.value.messages.firstOrNull()) {
            is UiEvent.Snackbar -> {
                val result = scaffoldState.snackbarHostState.showSnackbar(msg.msg, msg.action)
                if (result == SnackbarResult.ActionPerformed) {
                    // do undo operation
                    todoViewModel.undoDelete()
                }
                todoViewModel.consumeUiEvent(msg.id)
            }
            is UiEvent.Navigate -> {
                // Navigate to route
                onNavigate(msg.route)
                todoViewModel.consumeUiEvent(msg.id)
            }
            else -> {}
        }
    }
    Scaffold(scaffoldState = scaffoldState,
    floatingActionButton = {FloatingActionButton(onClick = { todoViewModel.navigateTodoAdd()}) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }}) {
        if (todos.value.isTodoListEmpty) {
            Text(text = ScreenStrings.LIST_EMPTY)
        } else {
            LazyColumn {
                items(todos.value.todos) { todo ->
                    TodoItem(todo = todo,
                        onDoneClicked = {todoViewModel.doneTodo(todo, !it.done)},
                        onDeleteClicked = { todoViewModel.deleteTodo(todo)},
                        onEditClicked = { todoViewModel.navigateTodoEdit(todo)})
                }
            }
        }
    }
}