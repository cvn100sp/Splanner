package com.windows.splanner

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.annotation.ColorInt
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ElevationOverlay
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.ui.BiasAlignment
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.style.TextAlign
import com.windows.resources.Res
import com.windows.splanner.UX.GoalCard
import org.jetbrains.skia.Surface
import java.lang.classfile.attribute.ModuleRequireInfo
import java.lang.module.ModuleFinder
import kotlin.Int
import kotlin.String
import androidx.compose.foundation.lazy.items
import java.lang.classfile.constantpool.IntegerEntry
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight

private var currentTaskWindow: Goal? by mutableStateOf(null)
private var currentSelection by mutableStateOf(1)
private var currentTaskWindowMode by mutableStateOf(1)
@Composable
fun MainActivity() { //id=1
    Row(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxHeight()
                .width(UX.MenuDpVal)
                .background(Color.LightGray),
            contentAlignment = Alignment.TopStart
        ) {
            Column (
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text="Меню",
                    fontSize = 24.sp,
                    modifier = Modifier.width(UX.MenuDpVal)
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(20.dp))
                UX.SectionButton(
                    "Активные цели",
                    MaterialTheme.colors.primary,
                    currentSelection == 1,
                    {
                        currentSelection=1
                        currentTaskWindow = null
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                UX.SectionButton(
                    "Архив",
                    MaterialTheme.colors.primary,
                    currentSelection == 2,
                    {
                        currentSelection=2
                        currentTaskWindow = null
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                UX.SectionButton(
                    "Корзина",
                    MaterialTheme.colors.primary,
                    currentSelection == 3,
                    {
                        currentSelection=3
                        currentTaskWindow = null
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.TopStart
        ){
            when{
                currentSelection>=1 -> {
                    Box(
                        modifier = Modifier.height(UX.HeaderDpVal)
                            .fillMaxWidth()
                            .padding(6.dp),
                        contentAlignment = Alignment.TopEnd
                    ){
                        var buttonName: String=""
                        var buttonicon: ImageVector=Icons.Default.Archive
                        var clickHandle:() -> Unit = {}
                        when(currentSelection){
                            1->{
                                buttonName="Создать"
                                buttonicon=Icons.Default.Addchart
                                clickHandle={addTaskAcctivity()}
                            }
                            2->{
                                buttonName="Восстановить все"
                                buttonicon=Icons.Default.Archive
                                clickHandle={TODO("")}
                            }
                            3->{
                                buttonName="Очистить корзину"
                                buttonicon=Icons.Default.DeleteForever
                                clickHandle={TODO("")}
                            }
                        }
                        UX.LiquidGlassButton(
                            buttonName,
                            buttonicon,
                            clickHandle
                        )
                    }
                    selection_tasks(currentSelection)
                }
                currentSelection == 0 -> selected_task_foreground_window(
                    onBack = {
                        currentSelection = currentTaskWindowMode
                        currentTaskWindow = null
                    }
                )
            }
        }
    }
    if (showAddGoalDialog) {
        AddGoalDialog(
            onDismiss = {
                showAddGoalDialog = false
            }
        )
    }
}
@Composable
fun selection_tasks(mode: Int) {

    var openedGoalId by remember(mode) {
        mutableStateOf<Int?>(null)
    }

    val goals = remember(mode, goalsRefreshKey) {
        when (mode) {
            1 -> GoalStorage.load()
            2 -> GoalArchiveStorage.load()
            3 -> GoalTrashedStorage.load()
            else -> emptyList()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top=UX.HeaderDpVal),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = goals,
            key = { goal -> goal.id }
        ) { goal ->

            SwipeGoalCard(
                goal = goal,

                isRevealed = openedGoalId == goal.id,

                onRevealChange = { opened ->
                    if (opened) {
                        openedGoalId = goal.id
                    } else if (openedGoalId == goal.id) {
                        openedGoalId = null
                    }
                },

                onClick = {
                    currentTaskWindow = goal
                    currentTaskWindowMode = mode
                    currentSelection = 0
                },

                onArchive = {
                    archiveGoal(goal.id)
                    openedGoalId = null
                    goalsRefreshKey++
                },

                onDelete = {
                    trashGoal(goal.id, mode)
                    openedGoalId = null
                    goalsRefreshKey++
                }
            )
        }
    }
}

@Composable
fun selected_task_foreground_window(
    onBack: () -> Unit
) {
    val initialGoal = currentTaskWindow

    if (initialGoal == null) {
        Text("Цель не выбрана")
        return
    }

    var editedGoal by remember(initialGoal.id) {
        mutableStateOf(initialGoal)
    }

    var isEditing by remember(initialGoal.id) {
        mutableStateOf(false)
    }

    // Обновление цели
    fun updateGoal(updated: Goal) {
        editedGoal = updated

        if (!isEditing) {
            currentTaskWindow = updated
            saveGoal(updated, currentTaskWindowMode)
        }
    }

    val goal = editedGoal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // Заголовок и кнопки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditing) {
                OutlinedTextField(
                    value = goal.title,
                    onValueChange = {
                        editedGoal = goal.copy(title = it)
                    },
                    label = { Text("Название цели") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            } else {
                Text(
                    text = goal.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.width(12.dp))

            if (isEditing) {
                TextButton(
                    onClick = {
                        editedGoal = currentTaskWindow ?: initialGoal
                        isEditing = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null
                    )
                    Spacer(Modifier.padding(2.dp))
                    Text("Отмена")
                }
            } else {
                TextButton(
                    onClick = {
                        isEditing = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = null
                    )
                    Spacer(Modifier.padding(2.dp))
                    Text("Изменить")
                }
            }

            TextButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )
                Spacer(Modifier.padding(2.dp))
                Text("Назад")
            }
        }

        // Описание
        if (isEditing) {
            OutlinedTextField(
                value = goal.description,
                onValueChange = {
                    editedGoal = goal.copy(description = it)
                },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        } else {
            Text(
                text = goal.description,
                fontSize = 15.sp
            )
        }

        // Срок
        Text(
            text = "Срок выполнения",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        GoalDatePicker(
            deadline = goal.deadline,
            onDateSelected = { date ->
                updateGoal(
                    goal.copy(deadline = date)
                )
            }
        )

        Divider()

        // Заголовок задач
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Задачи",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                "${goal.tasks.count { it.completed }}/${goal.tasks.size}",
                color = MaterialTheme.colors.primary
            )
        }

        // Прогресс
        LinearProgressIndicator(
            progress = goal.progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        // Список задач
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = goal.tasks,
                key = { it.id }
            ) { task ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = task.completed,
                        onCheckedChange = { checked ->

                            updateGoal(
                                goal.copy(
                                    tasks = goal.tasks.map {
                                        if (it.id == task.id) {
                                            it.copy(completed = checked)
                                        } else {
                                            it
                                        }
                                    }
                                )
                            )
                        }
                    )

                    if (isEditing) {
                        OutlinedTextField(
                            value = task.title,
                            onValueChange = { newTitle ->

                                editedGoal = goal.copy(
                                    tasks = goal.tasks.map {
                                        if (it.id == task.id) {
                                            it.copy(title = newTitle)
                                        } else {
                                            it
                                        }
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )

                        TextButton(
                            onClick = {
                                editedGoal = goal.copy(
                                    tasks = goal.tasks.filterNot {
                                        it.id == task.id
                                    }
                                )
                            }
                        ) {
                            Text("✕")
                        }
                    } else {
                        Text(
                            text = task.title,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        // Добавление задачи
        if (isEditing) {
            OutlinedButton(
                onClick = {
                    val nextId =
                        (goal.tasks.maxOfOrNull { it.id } ?: 0) + 1

                    editedGoal = goal.copy(
                        tasks = goal.tasks + GoalTask(
                            id = nextId,
                            title = "",
                            completed = false
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+ Добавить задачу")
            }

            // Сохранение
            Button(
                onClick = {
                    currentTaskWindow = editedGoal

                    saveGoal(
                        editedGoal,
                        currentTaskWindowMode
                    )

                    isEditing = false
                },
                enabled = goal.title.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить изменения")
            }
        }
    }
}