package com.valipu.composetodoapp.ui.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valipu.composetodoapp.ui.todo_list.UiEvent

@Composable
fun AddEditScreen(addEditTodoViewModel: AddEditTodoViewModel = hiltViewModel(), onPopBackStack: () -> Unit = {}) {
    val scaffoldState = rememberScaffoldState()
    val state = addEditTodoViewModel.todoUiState.collectAsState()

    LaunchedEffect(key1 = state.value.messages) {
        when(val msg = state.value.messages.firstOrNull()) {
            is UiEvent.PopBackStack -> {
                addEditTodoViewModel.consumeUiEvent(msg.id)
                onPopBackStack()
            }
            is UiEvent.Snackbar -> {
                scaffoldState.snackbarHostState.showSnackbar(msg.msg)
                addEditTodoViewModel.consumeUiEvent(msg.id)
            }
            else -> {}
        }
    }
    Scaffold(scaffoldState = scaffoldState, modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    floatingActionButton = { FloatingActionButton(onClick = { addEditTodoViewModel.saveTodo() }) {
        Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
    }}) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        TextField(value = addEditTodoViewModel.todoUiState.value.title, onValueChange = {title -> addEditTodoViewModel.setTitle(title)})
        TextField(value = addEditTodoViewModel.todoUiState.value.description ?: "", onValueChange = {description -> addEditTodoViewModel.setDescription(description)})
    }
    }
}

@Preview(showBackground = true, widthDp = 300)
@Composable
fun PreviewAddEditScreen() {
    AddEditScreen()
}