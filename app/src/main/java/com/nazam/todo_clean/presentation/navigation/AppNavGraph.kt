package com.nazam.todo_clean.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nazam.todo_clean.presentation.feature_task_edit.TaskEditScreen
import com.nazam.todo_clean.presentation.feature_task_edit.TaskEditViewModel
import com.nazam.todo_clean.presentation.feature_task_list.TaskListScreen

object Routes {
    const val TASK_LIST = "task_list"
    const val TASK_EDIT = "task_edit?taskId={taskId}"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {
        // 1) Liste
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                onAddClick = {
                    val noId = -1
                    navController.navigate("task_edit?taskId=$noId")
                }
            )
        }

        // 2) Édition (un SEUL bloc)
        composable(
            route = Routes.TASK_EDIT,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1

            // VM Hilt
            val vm: TaskEditViewModel = hiltViewModel()

            // Écoute l'état pour quitter si sauvegarde OK
            val uiState = vm.uiState.collectAsState()
            if (uiState.value.success) {
                vm.clearMessages()
                navController.popBackStack()
            }

            TaskEditScreen(
                taskId = taskId,
                onSave = { title, desc, priority ->
                    vm.saveTask(taskId, title, desc, priority)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}