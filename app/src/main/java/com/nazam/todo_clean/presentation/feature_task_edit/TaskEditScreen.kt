package com.nazam.todo_clean.presentation.feature_task_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nazam.todo_clean.domain.model.Priority

/**
 * Écran d'ajout/édition d'une tâche.
 * - Pas de ViewModel ici (on reste simple, un fichier à la fois)
 * - On remonte juste les infos via onSave(title, desc, priority)
 *
 * @param taskId si -1 ou null => mode "création", sinon "édition"
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    taskId: Int? = null,
    initialTitle: String = "",
    initialDescription: String = "",
    initialPriority: Priority = Priority.MEDIUM,
    onSave: (title: String, description: String, priority: Priority) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(TextFieldValue(initialTitle)) }
    var description by remember { mutableStateOf(TextFieldValue(initialDescription)) }
    var priority by remember { mutableStateOf(initialPriority) }
    var titleError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if ((taskId ?: -1) >= 0) "Edit task" else "New task") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                },
                actions = {
                    TextButton(onClick = {
                        if (title.text.isBlank()) {
                            titleError = "Title is required"
                        } else {
                            titleError = null
                            onSave(title.text.trim(), description.text.trim(), priority)
                        }
                    }) { Text("Save") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                isError = titleError != null,
                supportingText = { if (titleError != null) Text(titleError!!) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                minLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
            )

            // Sélecteur de priorité très simple (3 boutons)
            Text("Priority")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Priority.values().forEach { p ->
                    FilterChip(
                        selected = (priority == p),
                        onClick = { priority = p },
                        label = { Text(p.name) }
                    )
                }
            }
        }
    }
}