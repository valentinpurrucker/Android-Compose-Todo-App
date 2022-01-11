package com.valipu.composetodoapp.ui.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valipu.composetodoapp.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    onDoneClicked: (todo: Todo) -> Unit = {},
    onDeleteClicked: (todo: Todo) -> Unit = {},
    onEditClicked: (id: Int?) -> Unit = {},
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top, modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                expanded = !expanded
            }) {
        Row(verticalAlignment = Alignment.Top) {
            Column {

                Text(text = todo.title, style = MaterialTheme.typography.h6)
                if (todo.description != null) {
                    Text(text = todo.description, style = MaterialTheme.typography.body2)
                }

                if (expanded) {
                    OutlinedButton(onClick = { onDeleteClicked(todo) }) {
                        Text(text = "Delete todo")
                    }
                }
            }
            IconButton(onClick = { onEditClicked(todo.id ?: 0) }) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "Delete",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        Checkbox(checked = todo.done, onCheckedChange = { onDoneClicked(todo) })
    }
}


@Preview(showBackground = true, widthDp = 300)
@Composable
fun PreviewTodoItem() {
    TodoItem(todo = Todo(id = 1, title = "Title", description = "Description"))
}