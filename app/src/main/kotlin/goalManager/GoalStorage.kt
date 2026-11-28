package com.windows.splanner

import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private class GoalJsonStorage(fileName: String) {

    private val file = File(Sandboxed_storage, fileName)

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun load(): List<Goal> {
        if (!file.exists()) {
            return emptyList()
        }

        return json.decodeFromString(file.readText())
    }

    fun save(goals: List<Goal>) {
        file.parentFile?.mkdirs()
        file.writeText(json.encodeToString(goals))
    }
}

object GoalStorage {

    private val storage = GoalJsonStorage("goals.json")

    fun load(): List<Goal> = storage.load()

    fun save(goals: List<Goal>) = storage.save(goals)
}

object GoalArchiveStorage {

    private val storage = GoalJsonStorage("goals_archive.json")

    fun load(): List<Goal> = storage.load()

    fun save(goals: List<Goal>) = storage.save(goals)
}

object GoalTrashedStorage {

    private val storage = GoalJsonStorage("goals_trashed.json")

    fun load(): List<Goal> = storage.load()

    fun save(goals: List<Goal>) = storage.save(goals)
}

fun saveGoal(goal: Goal, mode: Int) {
    when (mode) {
        1 -> {
            val goals = GoalStorage.load().map {
                if (it.id == goal.id) goal else it
            }
            GoalStorage.save(goals)
        }

        2 -> {
            val goals = GoalArchiveStorage.load().map {
                if (it.id == goal.id) goal else it
            }
            GoalArchiveStorage.save(goals)
        }

        3 -> {
            val goals = GoalTrashedStorage.load().map {
                if (it.id == goal.id) goal else it
            }
            GoalTrashedStorage.save(goals)
        }
    }
}