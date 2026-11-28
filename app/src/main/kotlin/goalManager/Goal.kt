package com.windows.splanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate
import kotlinx.serialization.Serializable

var goalsRefreshKey by mutableIntStateOf(0)
var showAddGoalDialog by mutableStateOf(false)
@Serializable
data class Goal(
    val id: Int,
    val title: String,
    val description: String = "",

    @Serializable(with = LocalDateSerializer::class)
    val deadline: LocalDate? = null,

    val tasks: List<GoalTask> = emptyList()
) {
    val progress: Float
        get() = if (tasks.isEmpty()) 0f
        else tasks.count { it.completed }.toFloat() / tasks.size
}
@Serializable
data class GoalTask(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)

fun archiveGoal(id: Int) {
    val active = GoalStorage.load().toMutableList()

    val goal = active.firstOrNull { it.id == id } ?: return

    val archive = GoalArchiveStorage.load().toMutableList()

    if (archive.none { it.id == id }) {
        archive.add(goal)
    }

    GoalStorage.save(active.filterNot { it.id == id })
    GoalArchiveStorage.save(archive)
}

fun trashGoal(id: Int, sourceMode: Int) {
    val source = when (sourceMode) {
        1 -> GoalStorage.load()
        2 -> GoalArchiveStorage.load()
        else -> return
    }

    val goal = source.firstOrNull { it.id == id } ?: return

    val trash = GoalTrashedStorage.load().toMutableList()

    if (trash.none { it.id == id }) {
        trash.add(goal)
    }

    val updatedSource = source.filterNot { it.id == id }

    when (sourceMode) {
        1 -> GoalStorage.save(updatedSource)
        2 -> GoalArchiveStorage.save(updatedSource)
    }

    GoalTrashedStorage.save(trash)
}
fun emptyTrash() {
    GoalTrashedStorage.save(emptyList())
    goalsRefreshKey++
}
fun restoreAllGoals() {
    val trashed = GoalTrashedStorage.load()

    if (trashed.isEmpty()) return

    val active = GoalStorage.load().toMutableList()
    val archive = GoalArchiveStorage.load()

    val usedIds = (active + archive).map { it.id }.toMutableSet()

    var nextId = (
            active + archive + trashed
            ).maxOfOrNull { it.id }?.plus(1) ?: 1

    val restored = trashed.map { goal ->
        if (goal.id in usedIds) {
            goal.copy(id = nextId++).also {
                usedIds.add(it.id)
            }
        } else {
            usedIds.add(goal.id)
            goal
        }
    }

    GoalStorage.save(active + restored)
    GoalTrashedStorage.save(emptyList())

    goalsRefreshKey++
}
