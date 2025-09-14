package com.nazam.todo_clean.presentation.feature_task_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nazam.todo_clean.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    task: Task?,
    onBack: () -> Unit,
    onEdit: (taskId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task detail") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("←") }
                },
                actions = {
                    if (task != null) {
                        TextButton(onClick = { onEdit(task.id) }) { Text("Edit") }
                    }
                }
            )
        }
    ) { padding ->
        if (task == null) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Task not found")
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(task.title, style = MaterialTheme.typography.headlineSmall)
                if (task.description.isNotBlank()) {
                    Text(task.description, style = MaterialTheme.typography.bodyLarge)
                } else {
                    Text("No description", style = MaterialTheme.typography.bodyMedium)
                }
                AssistChip(
                    onClick = {},
                    label = { Text(task.priority.name) }
                )
                if (task.isDone) {
                    AssistChip(onClick = {}, label = { Text("DONE") })
                }
            }
        }
    }
}