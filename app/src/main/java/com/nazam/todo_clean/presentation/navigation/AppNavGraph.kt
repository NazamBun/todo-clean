package com.nazam.todo_clean.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nazam.todo_clean.presentation.feature_task_list.TaskListScreen

object Routes {
    const val TASK_LIST = "task_list"
    // taskId optionnel pour l’édition (null = création)
    const val TASK_EDIT = "task_edit?taskId={taskId}"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                // quand on cliquera sur le FAB → aller à l’écran d’édition (création)
                // On branchera ce lambda quand on aura mis à jour TaskListScreen (prochaine étape si tu veux)
            )
        }

        // Placeholder TEMPORAIRE pour compiler
        composable(
            route = Routes.TASK_EDIT,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { backStackEntry ->
            // On mettra TaskEditScreen ici dans la prochaine branche
            // Pour l’instant, simple texte
            androidx.compose.material3.Text("Task Edit (à venir)")
        }
    }
}