package com.windows.splanner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun AddGoalDialog(
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf<LocalDate?>(null) }

    val tasks = remember {
        mutableStateListOf<String>()
    }

    var newTask by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Новая цель")
        },

        text = {
            Column(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .heightIn(max = 550.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название цели") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Описание") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Text("Срок выполнения")

                GoalDatePicker(
                    deadline = deadline,
                    onDateSelected = {
                        deadline = it
                    }
                )

                Divider()

                Text(
                    text = "Задачи",
                    style = MaterialTheme.typography.h6
                )

                tasks.forEachIndexed { index, task ->

                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = task,
                            onValueChange = {
                                tasks[index] = it
                            },
                            modifier = Modifier.weight(1f),
                            label = { Text("Задача ${index + 1}") }
                        )

                        TextButton(
                            onClick = {
                                tasks.removeAt(index)
                            }
                        ) {
                            Text("✕")
                        }
                    }
                }

                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newTask,
                        onValueChange = { newTask = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Новая задача") },
                        singleLine = true
                    )

                    TextButton(
                        onClick = {
                            if (newTask.isNotBlank()) {
                                tasks.add(newTask.trim())
                                newTask = ""
                            }
                        }
                    ) {
                        Text("+")
                    }
                }
            }
        },

        confirmButton = {
            Button(
                onClick = {
                    val allGoals =
                        GoalStorage.load() +
                                GoalArchiveStorage.load() +
                                GoalTrashedStorage.load()

                    val newId =
                        (allGoals.maxOfOrNull { it.id } ?: 0) + 1

                    val goal = Goal(
                        id = newId,
                        title = title.trim(),
                        description = description.trim(),
                        deadline = deadline,
                        tasks = tasks.mapIndexed { index, task ->
                            GoalTask(
                                id = index + 1,
                                title = task,
                                completed = false
                            )
                        }
                    )

                    GoalStorage.save(
                        GoalStorage.load() + goal
                    )

                    goalsRefreshKey++
                    onDismiss()
                },
                enabled = title.isNotBlank()
            ) {
                Text("Создать")
            }
        },

        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Отмена")
            }
        }
    )
}