package com.valipu.composetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valipu.composetodoapp.ui.add_edit_todo.AddEditScreen
import com.valipu.composetodoapp.ui.theme.ComposeTodoAppTheme
import com.valipu.composetodoapp.ui.todo_list.NavigationRoutes
import com.valipu.composetodoapp.ui.todo_list.TodoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NavigationRoutes.TODO_LIST) {
                    composable(NavigationRoutes.TODO_LIST) {
                        TodoListScreen(onNavigate = {route -> navController.navigate(route)})
                    }
                    composable(NavigationRoutes.ADD + "?todoId={todoId}", arguments = listOf(
                        navArgument("todoId") {
                            type = NavType.IntType
                            defaultValue = -1
                        })) {
                        AddEditScreen(onPopBackStack = {navController.popBackStack()})
                    }
                }
        }
    }
}